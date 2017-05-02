/*
 * Copyright (c) 2017.
 */

package com.rae.swift;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.rae.swift.session.SessionManager;
import com.rae.swift.session.SessionToken;
import com.rae.swift.session.SessionUserInfo;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 会话管理器测试
 * Created by ChenRui on 2017/5/2 0002 16:01.
 */
@RunWith(AndroidJUnit4.class)
public class SessionManagerTest {

    @Test
    public void testToken() {
        SessionManager.initWithConfig(new SessionManager.ConfigBuilder().context(InstrumentationRegistry.getTargetContext()).tokenClass(SessionToken.class).userClass(SessionUserInfo.class).build());

        SessionToken token = new SessionToken();
        Log.i("rae", ((SessionToken) SessionManager.getDefault().getUserToken()).getAccessToken());
        token.setAccessToken("abc123");
        SessionManager.getDefault().setUserToken(token);
        token.setAccessToken("666");
        SessionManager.getDefault().setUserToken(token);
        Log.i("rae", ((SessionToken) SessionManager.getDefault().getUserToken()).getAccessToken());
    }
}
