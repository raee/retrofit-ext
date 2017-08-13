package com.squareup.okhttp3;

import android.content.Context;

import com.squareup.okhttp3.https.HttpsCertificateFactory;
import com.squareup.okhttp3.interceptor.CacheInterceptor;
import com.squareup.okhttp3.interceptor.LoggerInterceptor;
import com.squareup.okhttp3.interceptor.NetworkCacheInterceptor;

import java.io.File;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * OK HTTP CLIENT 扩展构造者
 * Created by ChenRui on 2017/4/25 0025 15:50.
 */
public class OkHttpExtBuilder {

    private OkHttpClient.Builder mBuilder;
    private boolean mEnableDebug;

    public OkHttpExtBuilder() {
        mBuilder = new OkHttpClient.Builder();
    }

    public OkHttpExtBuilder(OkHttpClient.Builder builder) {
        mBuilder = builder;
    }

    public OkHttpExtBuilder ssl(SSLSocketFactory sslFactory) {
        if (sslFactory != null) {
            mBuilder.sslSocketFactory(sslFactory);
            mBuilder.hostnameVerifier(HttpsCertificateFactory.hostnameVerifier());
        }
        return this;
    }

    /**
     * 忽略证书的HTTPS
     */
    public OkHttpExtBuilder https() {
        return ssl(HttpsCertificateFactory.getInstance());
    }

    /**
     * 从raw 文件中读取证书文件的HTTPS
     *
     * @param certificate raw 证书文件
     */
    public OkHttpExtBuilder https(Context context, int certificate) {
        if (certificate <= 0) throw new NullPointerException("证书文件不正确");
        return ssl(HttpsCertificateFactory.getInstance(context.getResources().openRawResource(certificate)));
    }

    /**
     * 是否启动调试模式
     */
    public OkHttpExtBuilder debug(String tag) {
        mEnableDebug = true;
        mBuilder.addInterceptor(new LoggerInterceptor(tag));
        return this;
    }

    /**
     * 开启缓存， 只对GET请求有效。
     */
    public OkHttpExtBuilder cache(Context context) {
        return cache(context, 30);
    }

    /**
     * 开启缓存， 只对GET请求有效。
     * 无网络情况下GET请求先从缓存获取，POST请求无缓存。
     *
     * @param maxAge 缓存时间
     */
    public OkHttpExtBuilder cache(Context context, int maxAge) {
        File dir = new File(context.getCacheDir(), "http-cache");
        mBuilder.cache(new Cache(dir, 1024 * 1024 * 50)); //50Mb
        mBuilder.addNetworkInterceptor(new NetworkCacheInterceptor(maxAge, mEnableDebug));
        mBuilder.addInterceptor(new CacheInterceptor(context));
        return this;
    }


    public OkHttpClient.Builder build() {
        return mBuilder;
    }

}
