package com.squareup.okhttp3.interceptor;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 日志记录拦截器
 * Created by ChenRui on 2017/4/25 0025 16:02.
 */
public class LoggerInterceptor implements Interceptor {

    private String mTag;

    public LoggerInterceptor(String tag) {
        mTag = tag;
    }

    public LoggerInterceptor() {
        this("HTTP-LOG");
    }

    private void log(String msg, Object... args) {
        String text = String.format(msg, args);
        // 超出大小
        int maxLength = 2048;
        if (text.length() > maxLength) {
            for (int i = 0; i < text.length(); i += maxLength) {
                int len = i + maxLength;
                if (len < text.length())
                    Log.d(mTag, text.substring(i, len));
                else
                    Log.d(mTag, text.substring(i, text.length()));
            }
        } else {
            Log.d(mTag, text);
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        log("==============================================================================");
        log("%s %s", request.method(), request.url());

        String headString = request.headers().toString();
        if (!TextUtils.isEmpty(headString)) {
            log("请求头：\n%s", headString);
            log("\n");
        }

        String requestBody = bufferRequestBody(request);
        if (requestBody != null) {
            log("%s", requestBody);
        }

//        headString = response.headers().toString();
//        if (!TextUtils.isEmpty(headString)) {
//            log("响应头：\n%s", headString);
//            log("\n");
//        }

        if (response.code() < 200 || response.code() >= 300) {
            log("请求错误：%d %s", response.code(), response.message());
        } else {
            String body = bufferBody(response);
            if (TextUtils.isEmpty(body)) {
                log("返回结果为空！");
            } else {
                log("返回结果：\n%s", body);
            }
        }

        return response;
    }

    private String bufferBody(Response response) {
        try {
            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.forName("UTF-8");
            return buffer.clone().readString(charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String bufferRequestBody(Request request) {
        try {
            if (request.body() == null || request.body().contentLength() <= 0) {
                return null;
            }

            Buffer sink;
            sink = new Buffer();
            request.body().writeTo(sink);
            return sink.readString(Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
