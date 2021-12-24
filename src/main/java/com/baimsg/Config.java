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
    public static String URL = "https://liaotianshi2022.com/api/im/imuser/login?";

    /**
     * 提交参数
     * 密码填参数说明：
     * 第一种：普通密码 就是没有加密的密码
     * 第二种：加密密码 就是密码需要加密的
     * 账号填参数说明：
     * 第一种：直接填账号 比如 121254088 就是不加密的
     * 第二种：[加密:账号] 比如 [加密:121254088] 就是账号需要加密的
     */
    public static String PARAM = "{\"username\":\"121254088\",\"pwd\":\"普通密码\"}";

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
     * 代理是否开启 (0 是关  1是开)
     */
    public static int IS_OPEN_PROXY = 0;

    /**
     * 代理刷新的延迟时间（单位毫秒/1000=1秒）
     */
    public static long PROXY_TIME_DELAY = 1000 * 3;

}
