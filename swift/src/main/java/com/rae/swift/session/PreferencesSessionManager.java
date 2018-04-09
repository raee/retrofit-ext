/*
 * Copyright (c) 2017.
 */

package com.rae.swift.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * 本地保存的状态
 * Created by ChenRui on 2017/5/2 0002 18:37.
 */
public class PreferencesSessionManager extends SessionManager {


    private final Config mConfig;
    private final Gson mGson = new Gson();
    private SharedPreferences mSharedPreferences;

    public PreferencesSessionManager(SessionManager.Config config) {
        mConfig = config;
        mSharedPreferences = config.context.getSharedPreferences("session", Context.MODE_PRIVATE);
    }

    @Override
    public boolean isLogin() {
        return getUser() != null;
    }

    @Override
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    @Override
    public <T> T getUser() {
        if (mConfig.userClass == null) return null;
        try {
            String json = mSharedPreferences.getString("users", null);
            if (TextUtils.isEmpty(json)) return null;
            return (T) mGson.fromJson(json, mConfig.userClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> void setUser(T user) {
        if (user == null) return;
        String json = mGson.toJson(user);
        mSharedPreferences.edit().putString("users", json).apply();
    }

    @Override
    public <T> void setUserToken(T token) {
        if (token == null) return;
        String json = mGson.toJson(token);
        mSharedPreferences.edit().putString("token", json).apply();
    }

    @Override
    public <T> T getUserToken() {
        if (mConfig.userTokenClass == null) return null;
        try {
            String json = mSharedPreferences.getString("token", null);
            if (TextUtils.isEmpty(json)) return null;
            return (T) mGson.fromJson(json, mConfig.userTokenClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
