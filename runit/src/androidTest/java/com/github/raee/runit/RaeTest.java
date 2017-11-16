package com.github.raee.runit;

import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ChenRui on 2017/4/27 0027 14:53.
 */
@RunWith(AndroidRUnit4ClassRunner.class)
public class RaeTest {

    @Test
    public void testApi() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("rae", "我是线程：" + Thread.currentThread().getId());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AndroidRUnit4ClassRunner.finish("testApi");
            }
        }).start();
    }
}
