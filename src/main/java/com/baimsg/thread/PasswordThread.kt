package com.baimsg.thread

import com.baimsg.common.Config
import com.baimsg.network.HttpUtils
import com.baimsg.utils.Log
import com.baimsg.utils.SafetyUtil
import com.baimsg.utils.extension.*
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
        val forms = mutableMapOf<String, String>()
        var param = Config.PARAM
        if (param.isNotBlank()) {
            param.split("&").forEach { s ->
                if (s.contains("=")) {
                    val values = s.split("=")
                    forms[values[0]] = values[1]
                }
            }
        }

        forms.apply {
            //处理密账号
            if (containsValue("加密账号") || param.contains("加密账号")) {
                param = param.replaceFirst("加密账号", userName.toMd5())
                putValue("加密账号", userName.toMd5())
            } else if (containsValue("普通账号") || param.contains("普通账号")) {
                param = param.replaceFirst("普通账号", userName)
                putValue("普通账号", userName)
            }
            //处理密码
            if (containsValue("普通密码") || param.contains("普通密码")) {
                param = param.replaceFirst("普通密码", password)
                putValue("普通密码", password)
            } else if (containsValue("加密密码") || param.contains("加密密码")) {
                param = param.replaceFirst("加密密码", password.toMd5())
                putValue("加密密码", password.toMd5())
            } else if (containsValue("pd5加密") || param.contains("pd5加密")) {
                putValue("pd5加密", "${Config.KEY}$password".toMd5())
            } else if (containsValue("校验加密") || param.contains("校验加密")) {
                if (!containsKey("time")) {
                    Log.e("校验加密 必须携带 time 参数！")
                    return
                }
                putValue("校验加密", password.toMd5())
                put("secret", "${Config.KEY}${get("time")}".toMd5())
            } else if (containsValue("mac加密") || param.contains("mac加密")) {
                if (!containsKey("salt")) {
                    Log.e("mac加密 必须携带 salt 参数！")
                    return
                }
                val passwordMd5 = SafetyUtil.md5Bytes(password.toByteArray())
                val key = SafetyUtil.bytesToHexString(
                    SafetyUtil.md5Bytes(
                        SafetyUtil.passWordAes(
                            passwordMd5, passwordMd5
                        )
                    )
                )
                val pass = SafetyUtil.macMd5(
                    "${Config.KEY}86$userName${get("salt")}".toByteArray(), key.toByteArray()
                ).toBase64Str()
                putValue("mac加密", pass)
                remove("secret")
                val treeMap = TreeMap<String, String>()
                treeMap.putAll(this)
                val sb = StringBuffer()
                for (value: String in treeMap.values) {
                    sb.append(value)
                }
                val hex = SafetyUtil.md5Bytes(Config.KEY.toByteArray())
                val secret = SafetyUtil.macMd5("${Config.KEY}$sb".toByteArray(), hex).toBase64Str()
                put("secret", secret)
            }
        }

        //处理请求头
        val headers = HashMap<String, String>()
        if (Config.HEADER.isNotBlank()) {
            Config.HEADER.split("\n".toRegex()).forEach { s ->
                if (s.contains(": ")) {
                    val arg = s.split(": ")
                    headers[arg[0]] = arg[1]
                }
            }
        }

        val body: String? = if (param.validateJson()) {
            HttpUtils.exePost(Config.URL, param, headers)
        } else {
            if (Config.type.equals("POST", ignoreCase = true)) {
                HttpUtils.exePost(Config.URL, forms, headers)
            } else {
                val url = StringBuilder()
                url.append(if (Config.URL.endsWith("?")) Config.URL else Config.URL + "?")
                var i = 0
                forms.forEach { (key, value) ->
                    url.append(if (i == 0) "" else "&")
                    url.append("$key=$value")
                    i++
                }
                HttpUtils.exeGet(url.toString(), headers)
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