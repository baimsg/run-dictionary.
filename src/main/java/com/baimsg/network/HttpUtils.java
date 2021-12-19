package com.baimsg.network;

import com.baimsg.Config;
import com.baimsg.bean.ProxyBean;
import com.baimsg.bean.ProxyInfo;
import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * create by baimsg 2021/11/17
 * Email 1469010683@qq.com
 **/
public class HttpUtils {
    private static ArrayList<ProxyInfo> proxyList = null;
    private static HttpUtils instance = null;
    private static OkHttpClient client = null;
    static HashMap<String, String> headers = new HashMap<>();

    static {
        headers.put("Connection", "close");
        if (Config.IS_OPEN_PROXY) {
            System.err.println("正在加载代理IP...");
            ProxyBean proxy = new Gson().fromJson(HttpUtils.build().exeGet(Config.PROXY_URL), ProxyBean.class);
            if (proxy != null) {
                if (proxy.getState() == 0) {
                    System.err.println("代理IP加载成功!");
                    proxyList = proxy.getData();
                }
            }
        }
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
        if (proxyList != null) {
            ProxyInfo proxy = proxyList.get(new Random().nextInt(proxyList.size() - 1));
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.retryOnConnectionFailure(false);
            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy.getIp(), proxy.getPort())));
            return builder.build();
        }
        if (client == null) {
            client = new OkHttpClient.Builder().build();
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
        Request request = new Request.Builder().get().url(url).headers(Headers.of(headers)).build();
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
//                .post(formBody.build())
                .method("POST", formBody.build()).url(url).headers(Headers.of(headers)).build();
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
        Request request = new Request.Builder().post(body).url(url).headers(Headers.of(headers)).build();
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
            String data = body == null ? "" : body.string();
            exe.close();
            return data;
        } catch (IOException e) {
            System.out.println(e);
            return e.getMessage();
        }
    }

}
