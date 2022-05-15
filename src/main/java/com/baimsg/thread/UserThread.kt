package com.baimsg.thread

import com.baimsg.common.Config
import com.baimsg.network.HttpUtils
import com.baimsg.utils.Log
import com.baimsg.utils.SafetyUtil
import com.baimsg.utils.extension.*
import com.baimsg.utils.toBase64Str
import org.json.JSONObject
import java.math.BigInteger
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random
import kotlin.random.nextLong

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
            if (containsKey("secret") && containsKey("time")) {
                put("secret", "${Config.KEY}${get("time")}".toMd5())
            } else if (containsKey("secret") && containsKey("salt")) {
                val treeMap = TreeMap<String, String>()
                treeMap.putAll(this)
                treeMap.remove("secret")
                treeMap.remove("salt")
                treeMap.remove("access_token")
                val sb = StringBuffer()
                for (value: String in treeMap.values) {
                    sb.append(value)
                }
                val salt = System.currentTimeMillis() + Random.nextInt(1, Config.maxThread)
                put("salt", "$salt")
                val secret = SafetyUtil.macMd5(
                    "${Config.KEY}10010698${get("access_token")}$sb$salt".toByteArray(),
                    Base64.getDecoder().decode("dgXE+gjQzM54U3QayWyXGQ==")
                ).toBase64Str()
                put("secret", secret)
            }

            //signature加密
            if (containsKey("signature") && containsKey("time")) {
                val time = System.currentTimeMillis()
                put("time", "$time")
                remove("signature")
                put("str", verify(time))
                val sb = StringBuffer()
                entries.sortedWith(compareBy { it.key }).forEach { (key, value) ->
                    if (sb.isNotEmpty()) sb.append("&")
                    sb.append("$key=$value")
                }
                put("signature", "${Config.START_KEY}$sb${Config.END_KEY}".toMd5())
            }
        }

        //处理请求头
        val headers = HashMap<String, String>()
        if (Config.HEADER.isNotEmpty()) {
            for (str in Config.HEADER.split("\n".toRegex())) {
                val arg = str.split(": ")
                headers[arg[0]] = arg[1]
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
            val msg = "$index\t[$userName] ->\t$json"
            output.write(msg)
            Log.i(msg)
        }
    }

    private fun verify(j: Long): String {
        val concat = Config.START_KEY + Config.END_KEY
        val substring = concat.substring(8, concat.length - 8)
        return "$j$substring".toMd5().lowercase(Locale.getDefault())
    }
}