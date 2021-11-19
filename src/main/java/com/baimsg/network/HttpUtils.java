package com.baimsg.network;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * create by baimsg 2021/11/17
 * Email 1469010683@qq.com
 **/
public class HttpUtils {

    private static HttpUtils instance = null;
    private static OkHttpClient client = null;
    static HashMap<String, String> headers = new HashMap<>();

    static {
        headers.put("Connection", "close");
    }

    /**
     * 获取 HttpUtils 类实例化对象
     *
     * @return HttpUtils 实例化对象
     */
    public static HttpUtils build() {
        if (instance == null) {
            instance = new HttpUtils();
        }
        return instance;
    }

    /**
     * 获取 OkHttpClient 实例化对象
     *
     * @return OkHttpClient 实例化对象
     */
    public static OkHttpClient buildClient() {
        if (client == null) {
            client = new OkHttpClient();
        }
        return client;
    }

    /**
     * 发送 GET请求
     *
     * @param url 链接
     * @return 请求结果
     */
    public String exeGet(String url) {
        return exeGet(url, headers);
    }

    /**
     * 发送 GET请求
     *
     * @param url     链接
     * @param headers 请求头
     * @return 请求结果
     */
    public String exeGet(String url, @NotNull Map<String, String> headers) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .headers(Headers.of(headers))
                .build();
        return exe(request);
    }

    /**
     * 发起 POST请求
     *
     * @param url  链接
     * @param form 表单集合
     * @return 请求结果
     */
    public String exePost(String url, Map<String, String> form) {
        return exePost(url, form, headers);
    }

    /**
     * 发起 POST请求
     *
     * @param url     链接
     * @param form    表单集合
     * @param headers 请求头
     * @return 请求结果
     */
    public String exePost(String url, Map<String, String> form, @NotNull Map<String, String> headers) {
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, String> entry : form.entrySet()) {
            formBody.add(entry.getKey(), entry.getValue());
        }
        Request request = new Request.Builder()
                .post(formBody.build())
                .url(url)
                .headers(Headers.of(headers))
                .build();
        return exe(request);
    }

    /**
     * 发起 POST请求
     *
     * @param url  链接
     * @param json json数据
     * @return 请求结果
     */
    public String exePost(String url, String json) {
        return exePost(url, json, headers);
    }

    public String exePost(String url, String json, @NotNull Map<String, String> headers) {
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.Companion.create(json, mediaType);
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .headers(Headers.of(headers))
                .build();
        return exe(request);
    }

    /**
     * 发起同步请求
     *
     * @param request 请求数据
     * @return 请求结果
     */
    private String exe(Request request) {
        try {
            Response exe = buildClient().newCall(request).execute();
            ResponseBody body = exe.body();
            return body == null ? "null" : body.string();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
