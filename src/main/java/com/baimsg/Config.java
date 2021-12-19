package com.baimsg;

/**
 * create by baimsg 2021/11/26
 * Email 1469010683@qq.com
 **/
public class Config {
    /**
     * 一次跑多少
     */
    public static int maxThread = 50;

    /**
     * 重试次数
     */
    public static int RETRY = 5;

    /**
     * 提交的URL地址
     */
    public static String URL = "http://121.40.90.152/LeBoLiaoBaApp/user/login?";

    /**
     * 提交参数
     */
    public static String PARAM = "password=测试&mobile=112520";

    /**
     * 提交的请求头
     */
    public static String HEADER = "";

    /**
     * 请求类型
     */
    public static String type = "POST";

    /**
     * 跑密码日志输出路径
     */
    public static String PATH = "./src/main/resources/log.ini";

    /**
     * 代理地址
     */
    public static String PROXY_URL = "http://api.sgxz.cn:12080/getip?token=61750c0f63bff03261cd5dac63ce1b06&protocol=HTTP&num=200&result_format=JSON&separator=%5Cn&ip_dedup=1&time_avail=1";

    /**
     * 代理是否开启
     */
    public static boolean IS_OPEN_PROXY = true;

}
