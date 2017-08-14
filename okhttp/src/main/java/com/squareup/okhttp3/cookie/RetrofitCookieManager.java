/*
 * Copyright (c) 2017.
 */

package com.squareup.okhttp3.cookie;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * cookie 管理器
 * Created by ChenRui on 2017/6/7 0007 15:02.
 */
public class RetrofitCookieManager implements CookieJar {

    private CookieManager mCookieManager;

    public RetrofitCookieManager() {
        mCookieManager = CookieManager.getInstance();

    }

    /**
     * 保存Cookie，通常服务端会返回Set-Cookie的头部，retrofit会回调这个方法
     */
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        String cookie = mCookieManager.getCookie(url.toString());
        if (TextUtils.isEmpty(cookie)) {
            cookie = "";
        }

        // 添加上去
        StringBuilder sb = new StringBuilder(cookie);
        for (Cookie c : cookies) {
            sb.append(" ");
            sb.append(c.toString());
            sb.append(";");
        }

        cookie = sb.toString();
        Log.i("rae", "设置cookie=" + cookie);
        mCookieManager.setCookie(url.toString(), cookie);
    }

    /**
     * 读取Cookie，当retrofit 发请求的时候会调用该方法设置Cookie
     */
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        String cookie = mCookieManager.getCookie(url.toString());
        Log.i("rae", "请求cookie=" + cookie);
        List<Cookie> cookies = new ArrayList<>();
        return cookies;
    }
}
