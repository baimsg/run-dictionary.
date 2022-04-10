package com.baimsg.common

import com.baimsg.utils.extension.appendPath
import com.baimsg.utils.extension.toFile
import java.util.HashSet

/**
 * create by baimsg 2021/11/26
 * Email 1469010683@qq.com
 */
object Config {
    var userNames = HashSet<String>()

    //这里填要跑的账号列表
    init {
        userNames.add("baimsg")
        userNames.add("test")
        userNames.add("last")
    }

    /**
     * 一次跑多少
     */
    var maxThread = 50

    /**
     * 重试次数
     */
    var RETRY = 5

    /**
     * 提交的URL地址
     */
    var URL = "http://api11190403.vhieo9.xyz:12845/api/user/search?"

    /**
     * 提交参数
     * 密码填参数说明：
     * 第一种：普通密码 就是没有加密的密码
     * 第二种：加密密码 就是密码需要加密的
     * 账号填参数说明：
     * 第一种：普通账号
     * 第二种：加密账号
     */
    var PARAM = "account=普通账号&sign=005a5f313fc344498a512b9ea1ca5868&timestamp=1649556938"

    /**
     * 提交的请求头
     */
    var HEADER = "User-Agent: okhttp/3.3.1\n" +
            "appkey: 005a5f313fc344498a512b9ea1ca5868\n" +
            "auth: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ3eCIsImF1ZCI6ImltX2FwcCIsImlhdCI6MTY0OTU4NzAyNiwibmJmIjoxNjQ5NTg3MDI2LCJleHAiOjE2NjUxMzkwMjYsInVpZCI6NzI1NjA1NjUsIm5hbWUiOiIxMTAwNjYifQ.6RB9Ntbtmq9yxgAQDL08Z_rWvEhP7Wle-aK67nJQCZ4\n" +
            "Content-Type: application/x-www-form-urlencoded; charset=utf-8\n" +
            "Host: api11190403.vhieo9.xyz:12845\n" +
            "Connection: close"

    /**
     * 请求类型
     */
    var type = "POST"

    /**
     * 代理地址
     */
    var PROXY_URL =
        "http://api.sgxz.cn:12080/getip?token=d3e0f7e450ede9c15059030709f17456&protocol=HTTP&num=200&result_format=JSON&separator=%5Cn&ip_dedup=1&time_avail=1"

    /**
     * 代理是否开启 (0 是关  1是开)
     */
    var IS_OPEN_PROXY = 1

    /**
     * 代理刷新的延迟时间（单位毫秒/1000=1秒）
     */
    var PROXY_TIME_DELAY = (1000 * 3).toLong()

    var OUT_PATH = "./src/main/resources/"

    /**
     * 密码输出路径
     */
    val PASSWORD_PATH = OUT_PATH.toFile().appendPath("password")

    /**
     * 账号输出路径
     */
    val USER_PATH = OUT_PATH.toFile().appendPath("user")
}