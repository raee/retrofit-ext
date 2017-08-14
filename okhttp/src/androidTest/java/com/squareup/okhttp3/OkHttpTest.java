package com.squareup.okhttp3;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OK HTTP TEST
 * Created by ChenRui on 2017/4/25 0025 16:33.
 */
@RunWith(AndroidJUnit4.class)
public class OkHttpTest {


    @Test
    public void testRequest() throws IOException, InterruptedException {

        final CacheControl.Builder cacheBuilder = new CacheControl.Builder();
//        cacheBuilder.noCache();//不使用缓存，全部走网络
//        cacheBuilder.noStore();//不使用缓存，也不存储缓存
//        cacheBuilder.onlyIfCached();//只使用缓存
//        cacheBuilder.noTransform();//禁止转码
        cacheBuilder.maxAge(10, TimeUnit.MILLISECONDS);//指示客户机可以接收生存期不大于指定时间的响应。
        cacheBuilder.maxStale(10, TimeUnit.SECONDS);//指示客户机可以接收超出超时期间的响应消息
        cacheBuilder.minFresh(10, TimeUnit.SECONDS);//指示客户机可以接收响应时间小于当前时间加上指定时间的响应。
        CacheControl cache = cacheBuilder.build();//cacheControl

        Context context = InstrumentationRegistry.getContext();
        OkHttpClient.Builder builder = new OkHttpExtBuilder().https().cache(context).debug("rae").build();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);

        OkHttpClient client = builder.build();
        Request request = new Request.Builder().url("http://192.168.100.169:808/test.php")
//                .post(new FormBody.Builder().build())
//                .cacheControl(cache)
                .build();
        Response response = client.newCall(request).execute();
        Thread.sleep(1000);
        response = client.newCall(request).execute();
        Thread.sleep(1000);
        response = client.newCall(request).execute();
        Thread.sleep(1000);
        response = client.newCall(request).execute();
        Log.i("rae", "请求成功！");
    }


    @Test
    public void testCookie() throws Exception {
        Context context = InstrumentationRegistry.getContext();
        OkHttpClient.Builder builder = new OkHttpExtBuilder().https().cookie().debug("rae").build();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);

        OkHttpClient client = builder.build();
        Request request = new Request.Builder().url("http://192.168.100.169:808/test.php")
//                .post(new FormBody.Builder().build())
//                .cacheControl(cache)
                .build();
        Response response = client.newCall(request).execute();
        Log.i("rae", "第一次响应：" + response.body().string());

        response = client.newCall(request).execute();
        Log.i("rae", "第二次响应：" + response.body().string());

    }
}
