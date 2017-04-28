package com.rae.session;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SessionManagerTest {


    @Test
    public void testSession() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        UserInfo user = new UserInfo();
        user.setName("我是RAE");
        SessionManager.getDefault().setUser(user);
        UserInfo sessionUser = SessionManager.getDefault().getUser();

        Log.i("Rae","会话的用户：" + sessionUser);

    }
}
