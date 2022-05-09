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
        userNames.add("a110066")
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

    var URL = "https://api.csmyim.com/mytio/login?"

    /**
     * 这里是密钥
     * 常见密钥
     * pd5 -> \${9DA015C1866616ABBE371EB25DED67E8}
     * mac -> 123 或 空
     * 麦聊 校验加密 -> 5e29f483c48848
     */
    var KEY = "\${9DA015C1866616ABBE371EB25DED67E8}"

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
    var PARAM = "p_is_android=1&_lau=cn&pd5=pd5加密&loginname=普通账号"

    /**
     * 提交的请求头
     */
    var HEADER =
        "Accept-Language: zh-CN,zh;q=0.8\n" + "User-Agent: tiohttp/watayouxiang\n" + "tio-deviceinfo: OPPO PCLM10\n" + "tio-imei: 353512023118299\n" + "tio-appversion: 5.0.2\\u000a\n" + "tio-cid: official\n" + "tio-resolution: 1080,1920\n" + "tio-operator: \\u4e2d\\u56fd\\u79fb\\u52a8\n" + "tio-size: 4.6\n" + "Content-Type: application/x-www-form-urlencoded\n" + "Content-Length: 75\n" + "Host: api.csmyim.com\n" + "Connection: Keep-Alive\n" + "Cookie: tio_session=15123457325157851535156375552"

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