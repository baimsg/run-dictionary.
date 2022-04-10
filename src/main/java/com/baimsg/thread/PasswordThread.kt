package com.baimsg.thread

import com.baimsg.common.Config
import com.baimsg.network.HttpUtils
import com.baimsg.utils.extension.appendPath
import com.baimsg.utils.extension.toFile
import com.baimsg.utils.extension.toMd5
import com.baimsg.utils.extension.validateJson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.json.JSONObject
import java.math.BigInteger


class PasswordThread(
    private val index: BigInteger, private val userName: String, private val password: String
) : Runnable {
    override fun run() {
        val output = Config.OUT_PATH.toFile().appendPath("password")
        var param = Config.PARAM
        //处理密码
        param = if (param.contains("加密账号")) {
            param.replaceFirst("加密账号", userName.toMd5())
        } else {
            param.replaceFirst("普通账号", userName)
        }

        //处理账号
        param = if (param.contains("加密密码")) {
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
        val msg = "$index\t密码：$password"
//        output.append(msg + "\t" + JSONObject(body))
        println(msg + "\t" + JSONObject(body))
    }


}