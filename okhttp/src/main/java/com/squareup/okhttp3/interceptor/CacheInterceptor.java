package com.squareup.okhttp3.interceptor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存拦截器， 需要结合{@link NetworkCacheInterceptor}来一起使用。
 * {@link okhttp3.OkHttpClient.Builder#addInterceptor(Interceptor)}
 * Created by ChenRui on 2017/4/25 0025 17:14.
 */
public class CacheInterceptor implements Interceptor {


    private Context mContext;

    public CacheInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // 只有GET方法才缓存
        if (request.method().equalsIgnoreCase("GET") && !isConnected()) {
            Log.e("rae", "无网络连接，从缓存中读取！");
            request = chain.request().newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }

        return chain.proceed(request);

    }

    // 检查网络是否连接
    private boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null)
            return false;
        return networkInfo.isConnected() && networkInfo.isAvailable();
    }
}
