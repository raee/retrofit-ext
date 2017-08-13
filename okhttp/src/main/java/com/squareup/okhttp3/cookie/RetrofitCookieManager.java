/*
 * Copyright (c) 2017.
 */

package com.squareup.okhttp3.cookie;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * CookieManager
 * Created by ChenRui on 2017/6/6 23:52.
 */
public class RetrofitCookieManager implements CookieJar {

    // 系统的
    CookieManager mCookieManager;

    private Map<String, String> mCookieMap = new HashMap<>();

    public RetrofitCookieManager() {
        mCookieManager = CookieManager.getInstance();
        // 加载初始化的cookie

    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        loadCookie(url);
        // 保存cookie
        for (Cookie cookie : cookies) {
            Log.d("Rae", cookie.toString());

        }
    }

    private void loadCookie(HttpUrl url) {
        // 获取原来的
        String src = mCookieManager.getCookie(url.toString());
        if (TextUtils.isEmpty(src)) return;


    }

    public void addCookie(String url, Cookie cookie) {

    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        // 读取cookie
        List<Cookie> cookies = new ArrayList<>();
        return cookies;
    }
}
