package com.baimsg.thread

import com.baimsg.common.Config
import com.baimsg.network.HttpUtils
import com.baimsg.utils.Log
import com.baimsg.utils.SafetyUtil
import com.baimsg.utils.extension.append
import com.baimsg.utils.extension.appendPath
import com.baimsg.utils.extension.toMd5
import com.baimsg.utils.extension.validateJson
import com.baimsg.utils.toBase64Str
import org.json.JSONObject
import java.math.BigInteger
import java.util.TreeMap


class PasswordThread(
    private val index: BigInteger, private val userName: String, private val password: String
) : Runnable {
    override fun run() {
        synchronized(this) {
            HttpUtils.run = true
        }

        val output = Config.PASSWORD_PATH.appendPath("${userName}.ini")
        var param = Config.PARAM
        //处理密账号
        param = if (param.contains("加密账号")) {
            param.replaceFirst("加密账号", userName.toMd5())
        } else {
            param.replaceFirst("普通账号", userName)
        }

        //处理密码
        param = if (param.contains("AES密码")) {
            var salt = ""
            param.split("&").forEach { s ->
                val values = s.split("=")
                if (values[0] == "salt") {
                    salt = values[1]
                    return@forEach
                }
            }
            val passwordMd5 = SafetyUtil.md5Bytes(password.toByteArray())
            val key = SafetyUtil.bytesToHexString(SafetyUtil.md5Bytes(SafetyUtil.passWordAes(passwordMd5, passwordMd5)))
            val pass = SafetyUtil.macMd5("12386$userName$salt".toByteArray(), key.toByteArray()).toBase64Str()
            val para = param.replaceFirst("AES密码", pass)
            val map = TreeMap<String, String>()
            para.split("&").forEach { s ->
                val values = s.split("=")
                if (values[0] == "salt") {
                    salt = values[1]
                } else {
                    map[values[0]] = values[1]
                }
            }
            val sb = StringBuffer()
            for (value: String in map.values) {
                sb.append(value)
            }
            val hex = SafetyUtil.md5Bytes("123".toByteArray())
            val secret = SafetyUtil.macMd5("123$sb$salt".toByteArray(), hex).toBase64Str()
            "$para&secret=$secret"
        } else if (param.contains("加密密码")) {
            param.replaceFirst("加密密码", password.toMd5())
        } else {
            param.replaceFirst("普通密码", password)
        }
        //处理请求头
        val headers = HashMap<String, String>()
        if (Config.HEADER.isNotEmpty()) {
            for (str in Config.HEADER.split("\n".toRegex())) {
                val arg = str.split(": ")
                headers[arg[0]] = arg[1]
            }
        }

        val body: String?
        if (param.validateJson()) {
            body = HttpUtils.exePost(Config.URL, param, headers)
        } else {
            if (Config.type.equals("POST", ignoreCase = true)) {
                val forms = HashMap<String, String>()
                for (str in param.split("&")) {
                    val arg = str.split("=")
                    if (arg.size < 2) return
                    forms[arg[0]] = arg[1]
                }
                body = HttpUtils.exePost(Config.URL, forms, headers)
            } else {
                body = HttpUtils.exeGet(Config.URL + "?" + param, headers)
            }
        }
        body?.let { data ->
            val json = try {
                JSONObject(data)
            } catch (e: Exception) {
                data
            }
            val msg = "$index\t[$userName] -> \t密码：$password\t$json"
            output.append(msg)
            Log.i(msg)
        }

    }


}