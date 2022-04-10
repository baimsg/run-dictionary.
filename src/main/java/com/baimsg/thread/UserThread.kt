package com.baimsg.thread

import com.baimsg.common.Config
import com.baimsg.network.HttpUtils
import com.baimsg.utils.Log
import com.baimsg.utils.extension.append
import com.baimsg.utils.extension.appendPath
import com.baimsg.utils.extension.validateJson
import org.json.JSONObject
import java.math.BigInteger

class UserThread(private val index: BigInteger, private val userName: String) : Runnable {
    override fun run() {
        if (!Config.USER_PATH.exists()) {
            Log.d("路径 -> ${Config.USER_PATH} 创建${if (Config.USER_PATH.mkdirs()) "成功" else "失败"}")
        }
        val output = Config.USER_PATH.appendPath("${userName}.ini")
        if (output.isFile) {
            output.delete()
            Log.d("${output.name} -> 数据已清空！")
        }
        var param = Config.PARAM
        //处理账号
        param = param.replaceFirst("普通账号", userName)

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
        body?.let {
            val msg = "$index\t[$userName] ->\t${JSONObject(body)}"
            output.append(msg)
            Log.i(msg)
        }
    }
}