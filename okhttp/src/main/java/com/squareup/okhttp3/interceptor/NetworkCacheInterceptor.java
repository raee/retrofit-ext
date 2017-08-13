package com.squareup.okhttp3.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存拦截器， 使用：
 * {@link okhttp3.OkHttpClient.Builder#addNetworkInterceptor(Interceptor)}
 * Created by ChenRui on 2017/4/25 0025 17:14.
 */
public class NetworkCacheInterceptor implements Interceptor {


    private int mDefaultMaxAge;
    private boolean mEnableDebug;

    public NetworkCacheInterceptor(int maxAge, boolean enableDebug) {
        this(30);
        mEnableDebug = enableDebug;
    }

    public NetworkCacheInterceptor(int maxAge) {
        this.mDefaultMaxAge = maxAge;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        // 如果客户端带了缓存，而服务端没有返回对应的缓存信息。
        CacheControl control = request.cacheControl();

        // 缓存时间
        int maxAge = (control != null && control.maxAgeSeconds() >= 0) ? control.maxAgeSeconds() : mDefaultMaxAge;

        if (mEnableDebug) {
            Log.w("rae", String.format("客户端请求：[%d] [%s]\n%s", maxAge, request.url().toString(), request.headers().toString()));
        }

        response = response.newBuilder()
                .removeHeader("Cache-Control")
                .removeHeader("Pragma")
                .addHeader("Cache-Control", "public, max-age=" + maxAge)
                .build();

        return response;
    }
}
