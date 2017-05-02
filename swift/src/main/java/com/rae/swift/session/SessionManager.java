/*
 * Copyright (c) 2017.
 */

package com.rae.swift.session;


import android.content.Context;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * 会话管理
 * Created by ChenRui on 2017/4/28 0028 17:27.
 */
public abstract class SessionManager {

    public static class Config {
        Class<?> userTokenClass;
        Class<?> userClass;
        Context context;
    }

    public static class ConfigBuilder {
        private final Config mConfig;

        public ConfigBuilder() {
            mConfig = new Config();
        }

        public ConfigBuilder tokenClass(Class<?> cls) {
            mConfig.userTokenClass = cls;
            return this;
        }

        public ConfigBuilder userClass(Class<?> cls) {
            mConfig.userClass = cls;
            return this;
        }

        public ConfigBuilder context(Context applicationContext) {
            mConfig.context = applicationContext;
            return this;
        }

        public Config build() {
            return mConfig;
        }
    }

    private static Config sConfig;
    private static WeakReference<SessionManager> managerWeakReference;

    /**
     * 获取默认的会话管理器，默认的为cookie 管理器。
     * 使用之前请使用{@link #initWithConfig(Config)} 来进行初始化配置。
     */
    public static SessionManager getDefault() {
        if (sConfig == null) {
            Log.w("SessionManager", "session config from default");
            sConfig = new ConfigBuilder().tokenClass(SessionToken.class).userClass(SessionUserInfo.class).build();
        }

        if (managerWeakReference == null || managerWeakReference.get() == null) {
            synchronized (SessionManager.class) {
                if (managerWeakReference == null || managerWeakReference.get() == null) {
                    managerWeakReference = new WeakReference<SessionManager>(new PreferencesSessionManager(sConfig));
                }
            }
        }

        return managerWeakReference.get();
    }

    /**
     * 初始化会话管理器
     */
    public static void initWithConfig(Config config) {
        if (sConfig != null) {
            sConfig = null;
            System.gc();
        }

        sConfig = config;
    }


    SessionManager() {
    }

    /**
     * 是否登录
     */
    public abstract boolean isLogin();


    /**
     * 清除会话信息，即退出登录。
     */
    public abstract void clear();

    /**
     * 获取当前登录的用户信息，在调用该方法之前请先调用{@link #isLogin()}来判断是否登录
     */
    public abstract <T> T getUser();

    /**
     * 设置当前用户信息
     */
    public abstract <T> void setUser(T user);

    /**
     * 设置用户授权信息
     *
     * @param token 授权信息
     */
    public abstract <T> void setUserToken(T token);

    /**
     * 获取用户授权信息
     */
    public abstract <T> T getUserToken();


}
