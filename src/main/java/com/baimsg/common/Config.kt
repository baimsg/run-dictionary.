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

    var URL = "http://152.32.187.50:8094/auth/getLoginCode"

    /**
     * 这里是密钥
     * 常见密钥
     * pd5 -> \${9DA015C1866616ABBE371EB25DED67E8}
     * mac -> 123 或 空
     * 麦聊 校验加密 -> 5e29f483c48848
     */
    var KEY = "123"

    /**
     * 提交参数
     * 账号填参数说明：
     * 第一种：普通账号
     * 第二种：加密账号
     * 密码填参数说明：
     * 第一种：普通密码 就是没有加密的密码
     * 第二种：加密密码 就是密码需要加密的
     * 第三中：mac加密 不要填 secret 参数【记得填密钥】
     * 第四种：pd5加密 【记得填密钥】
     * 第五中：校验加密 不要填 secret 参数【记得填密钥】
     */
    var PARAM = "areaCode=86&deviceId=android&account=普通账号&mac=mac加密&language=zh&salt=1652183032502"

    /**
     * 提交的请求头
     */
    var HEADER = "User-Agent: shiku_im/1.4.7 (Linux; U; Android 11; Mi9 Pro 5G Build/RKQ1.200826.002)\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Content-Length: 143\n" +
            "Host: 152.32.187.50:8094\n" +
            "Connection: Keep-Alive\n" +
            "Accept-Encoding: gzip"

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
    var IS_OPEN_PROXY = 0

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