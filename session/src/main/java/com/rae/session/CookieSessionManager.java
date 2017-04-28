//package com.rae.session;
//
//import android.text.TextUtils;
//import android.webkit.CookieManager;
//
///**
// * 保存在Cookie中的Session管理器
// * Created by ChenRui on 2017/4/28 0028 17:31.
// */
//class CookieSessionManager extends SessionManager {
//
//    private final CookieManager mCookieManager;
//    private final Config mConfig;
//    private String mBaseUrl;
//    private SessionParcelableConverter mUser; // 当前登录用户
//
//    CookieSessionManager(SessionManager.Config config) {
//        mConfig = config;
//        mCookieManager = CookieManager.getInstance();
//        mBaseUrl = "session://com.rae.session/";
//    }
//
//    private String tokenUrl() {
//        return mBaseUrl + "token";
//    }
//
//    private String userUrl() {
//        return mBaseUrl + "user";
//    }
//
//    private void setCookie(String url, String value) {
//        mCookieManager.setCookie(url, value);
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
//    @SuppressWarnings("unchecked")
//    public <T extends SessionParcelableConverter> T getUser() {
//
//        // 返回当前的用户
//        if (mUser != null)
//            return (T) mUser;
//
//        String cookie = mCookieManager.getCookie(userUrl());
//        if (TextUtils.isEmpty(cookie)) return null;
//
//        // 反射解析
//        try {
//            clas
//                    mUser = convert(cookie);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return (T) mUser;
//    }
//
//
//    @Override
//    public <T extends UserToken> void setUserToken(T token) {
//
//    }
//
//    @Override
//    public <T extends UserToken> T getUserToken() {
//        return null;
//    }
//}
