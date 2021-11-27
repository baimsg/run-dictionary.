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
    public static String URL = "http://119.23.35.153:6661/api/exclude/login";

    /**
     * 提交参数
     */
    public static String PARAM = "{\"account\":\"112233\",\"loginType\":1,\"password\":\"测试\",\"phone\":0,\"sex\":0,\"source\":\"mobile\"}";


    /**
     * 提交的请求头
     */
    public static String HEADER =
            "client: Android\n" +
                    "w-client: Android\n" +
                    "w-uuid: c10054e544402ed0\n" +
                    "w-brand: OPPO\n" +
                    "w-systemVersion: 10\n" +
                    "w-versionCode: 20210516\n" +
                    "w-versionName: 9.20.6\n" +
                    "w-enable-mode: 1\n" +
                    "Content-Type: application/json; charset=UTF-8\n" +
                    "Connection: close";

    /**
     * 请求类型
     */
    public static String type = "POST";

    /**
     * 跑密码日志输出路径
     */
    public static String PATH = "./src/main/resources/log.ini";

}
