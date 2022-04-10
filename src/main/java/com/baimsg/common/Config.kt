package com.baimsg.common

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
    var URL = "https://liaotianshi2022.com/api/im/imuser/login?"

    /**
     * 提交参数
     * 密码填参数说明：
     * 第一种：普通密码 就是没有加密的密码
     * 第二种：加密密码 就是密码需要加密的
     * 账号填参数说明：
     * 第一种：普通账号
     * 第二种：加密账号
     */
    var PARAM = "{\"username\":\"普通账号\",\"pwd\":\"普通密码\"}"

    /**
     * 提交的请求头
     */
    var HEADER = ""

    /**
     * 请求类型
     */
    var type = "POST"

    /**
     * 代理地址
     */
    var PROXY_URL =
        "http://api.sgxz.cn:12080/getip?token=61750c0f63bff03261cd5dac63ce1b06&protocol=HTTP&num=200&result_format=JSON&separator=%5Cn&ip_dedup=1&time_avail=1"

    /**
     * 代理是否开启 (0 是关  1是开)
     */
    var IS_OPEN_PROXY = 1

    /**
     * 代理刷新的延迟时间（单位毫秒/1000=1秒）
     */
    var PROXY_TIME_DELAY = (1000 * 3).toLong()

    var OUT_PATH = "./src/main/resources/"

}