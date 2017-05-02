///*
// * Copyright (c) 2017.
// */
//
//package com.rae.swift.session;
//
//import android.text.TextUtils;
//import android.webkit.CookieManager;
//
//import com.google.gson.Gson;
//
///**
// * 保存在Cookie中的Session管理器
// * Created by ChenRui on 2017/4/28 0028 17:31.
// */
//class CookieSessionManager extends SessionManager {
//
//    private final CookieManager mCookieManager;
//    private final Config mConfig;
//    private final Gson mGson = new Gson();
//    private final static String SESSION_NAME = ".RaeSession=";
//
//    CookieSessionManager(SessionManager.Config config) {
//        mConfig = config;
//        mCookieManager = CookieManager.getInstance();
//    }
//
//    private String tokenUrl() {
//        return "session://token.rae.session/";
//    }
//
//    private String userUrl() {
//        return "session://user.rae.session/";
//    }
//
//    private void setCookie(String url, String value) {
//        mCookieManager.setCookie(url, SESSION_NAME + value);
//    }
//
//    @Override
//    public boolean isLogin() {
//        return getUser() != null;
//    }
//
//    @Override
//    public void clear() {
//        setCookie(tokenUrl(), null);
//        setCookie(userUrl(), null);
//    }
//
//    @Override
//    public <T> T getUser() {
//        if (mConfig.userClass == null) return null;
//        try {
//            String json = mCookieManager.getCookie(userUrl());
//            if (!TextUtils.isEmpty(json)) {
//                @SuppressWarnings("unchecked")
//                Class<? extends T> cls = (Class<? extends T>) mConfig.userClass;
//                return json2Object(cls, json);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public <T> void setUser(T user) {
//        String json = object2Json(user);
//        if (json != null) {
//            setCookie(userUrl(), json);
//        }
//    }
//
//
//    @Override
//    public <T> void setUserToken(T token) {
//        String json = object2Json(token);
//        if (json != null) {
//            setCookie(tokenUrl(), json);
//        }
//    }
//
//    @Override
//    public <T> T getUserToken() {
//        if (mConfig.userTokenClass == null) return null;
//        try {
//            String json = mCookieManager.getCookie(tokenUrl());
//            if (!TextUtils.isEmpty(json)) {
//                @SuppressWarnings("unchecked")
//                Class<? extends T> cls = (Class<? extends T>) mConfig.userTokenClass;
//                return json2Object(cls, json);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private <T> T json2Object(Class<T> type, String json) {
//        return mGson.fromJson(json.replace(SESSION_NAME, ""), type);
//    }
//
//
//    // 对象转json
//    private <T> String object2Json(T obj) {
//        return mGson.toJson(obj);
//    }
//}
