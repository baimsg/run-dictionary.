package com.baimsg.network

import com.baimsg.bean.ProxyInfo
import com.baimsg.bean.ProxyItem
import com.baimsg.common.Config
import com.baimsg.utils.Log
import com.google.gson.Gson
import okhttp3.*
import okhttp3.Headers.Companion.toHeaders
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.log

object HttpUtils {

    var run = false

    private val proxyList: MutableList<ProxyInfo> = mutableListOf()

    private var headers = HashMap<String, String>()

    private var client: OkHttpClient? = null

    init {
        headers["Connection"] = "close"
        if (Config.IS_OPEN_PROXY == 1) {
            Log.d("[IP] -> loading")
            //加载代理IP
            loadProxy()
            //开启自动刷新代理IP
            startRefreshProxy()
        }

    }

    /**
     * 获取 OkHttpClient 实例化对象
     *
     * @return OkHttpClient 实例化对象
     */
    private fun buildClient(isProxy: Boolean = true): OkHttpClient {
        if (isProxy && proxyList.isNotEmpty()) {
            val (ip, port) = proxyList.random()
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(60, TimeUnit.SECONDS)
            builder.readTimeout(40, TimeUnit.SECONDS)
            builder.writeTimeout(30, TimeUnit.SECONDS)
            builder.retryOnConnectionFailure(false)
            builder.proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress(ip, port)))
            return builder.build()
        }
        return client ?: synchronized(this) {
            val instance = OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).build()
            client = instance
            return instance
        }
    }

    /**
     * 加载代理IP地址和端口（自动刷新的）
     */
    private fun startRefreshProxy() {
        Thread {
            while (true) {
                if (!run) {
                    Log.d("[IP] -> end")
                    break
                }
                try {
                    Thread.sleep(Config.PROXY_TIME_DELAY)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                loadProxy()
            }
        }.start()
    }

    /**
     * 刷新代理IP
     */
    private fun loadProxy() {
        val request = Request.Builder().get().url(Config.PROXY_URL).headers(headers.toHeaders()).build()
        try {
            val exe = buildClient(false).newCall(request).execute()
            val body = exe.body
            if (body != null) {
                val proxy = Gson().fromJson(String(body.bytes()), ProxyItem::class.java)
                if (proxy != null) {
                    if (proxy.state == 0) {
                        Log.d("[IP] -> update!")
                        proxyList.clear()
                        proxyList.addAll(proxy.data)
                    } else {
                        Log.e("[IP] " + proxy.msg)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    /**
     * 发送 GET请求
     *
     * @param url 链接
     * @return 请求结果
     */
    fun exeGet(
        url: String, headers: Map<String, String> = mutableMapOf("Connection" to "close")
    ): String? {
        val request = Request.Builder().get().url(url).headers(headers.toHeaders()).build()
        return exe(request)
    }

    /**
     * 发起 POST请求
     *
     * @param url  链接
     * @param form 表单集合
     * @return 请求结果
     */
    @JvmOverloads
    fun exePost(
        url: String, form: Map<String, String>, headers: Map<String, String> = hashMapOf("Connection" to "close")
    ): String? {
        val formBody = FormBody.Builder()
        for ((key, value) in form) {
            formBody.add(key, value)
        }
        val request = Request.Builder().post(formBody.build())
//            .method("POST", formBody.build())
            .url(url).headers(headers.toHeaders()).build()
        return exe(request)
    }

    /**
     * 发起 POST请求
     *
     * @param url  链接
     * @param json json数据
     * @return 请求结果
     */
    @JvmOverloads
    fun exePost(
        url: String, json: String, headers: Map<String, String> = hashMapOf("Connection" to "close")
    ): String? {
        val mediaType = "application/json;charset=utf-8".toMediaTypeOrNull()
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder().post(body).url(url).headers(headers.toHeaders()).build()
        return exe(request)
    }

    /**
     * 发起同步请求
     *
     * @param request 请求数据
     * @return 请求结果
     */
    private fun exe(request: Request): String? {
        for (i in 1..Config.RETRY) {
            if (i != 1) Log.d("${request.url} 第${i}次重试ing...")
            try {
                val exe = buildClient().newCall(request).execute()
                val body = exe.body
                val msg = body?.string() ?: "null"
                body?.close()
                if (exe.isSuccessful) {
                    return msg
                } else {
                    Log.e(exe.message)
                }
            } catch (e: IOException) {
                e.message
            }
        }

        Log.e("[${request.method}:${request.url} ${request.body?.contentType()}] -> error")
        return null
    }

}