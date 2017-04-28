package com.rae.session;


import android.content.Context;

/**
 * 会话管理
 * Created by ChenRui on 2017/4/28 0028 17:27.
 */
public abstract class SessionManager {

    public class Config {

        Class<? extends UserToken> userTokenClass;

        public Config withContext(Context context) {

            return this;
        }

        public Config tokenClass(Class<? extends UserToken> cls) {
            userTokenClass = cls;
            return this;
        }

    }

    public static SessionManager getDefault(Config config) {
        return new CookieSessionManager(config);
    }

    protected SessionManager() {
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
    public abstract <T extends UserToken> void setUserToken(T token);

    /**
     * 获取用户授权信息
     */
    public abstract <T extends UserToken> T getUserToken();


}
