package com.baimsg.thread;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.Hpack;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static sun.misc.Version.println;

/**
 * create by baimsg 2021/11/20
 * Email 1469010683@qq.com
 **/
public class LogInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        Buffer buffer = new Buffer();
        //将请求体内容写入buffer中
        requestBody.writeTo(buffer);
        System.out.println(new String(buffer.readByteArray(), StandardCharsets.UTF_8));
        return chain.proceed(request);
    }
}
