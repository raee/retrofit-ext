/*
 * Copyright (c) 2017.
 */

package com.rae.swift;

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
public class ExampleInstrumentedTest {

    private interface ITestConfig {
        void setName(String name);

        String getName();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        RaeSharedPreferences preferences = new RaeSharedPreferences(appContext);
        ITestConfig config = preferences.create(ITestConfig.class);
        config.setName("我是测试");
        Log.i("Rae", config.getName());

    }
}
