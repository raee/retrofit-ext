/*
 * Copyright (c) 2017.
 */

package com.rae.plugin.gitlab;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * gitlab request
 * Created by ChenRui on 2017/6/20 0020 16:42.
 */
class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("PRIVATE-TOKEN", GitLabJenkinsUrls.GITLAB_PRIVATE_TOKEN)
                .build();
        return chain.proceed(request);
    }
}
