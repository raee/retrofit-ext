package com.squareup.okhttp3.interceptor;

import android.text.TextUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

/**
 * TOKEN 拦截器
 * Created by ChenRui on 2017/4/12 00:19.
 */
public abstract class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = onInterceptRequest(chain.request());

        Response response = chain.proceed(request);

        String body = bufferBody(response);

        // 判断Token是否过期
        try {
            if (!TextUtils.isEmpty(body) && invalidateToken(request, response, body)) {
                // 刷新TOKEN
                String token = refreshToken(body);

                if (TextUtils.isEmpty(token)) {
                    return response;
                }

                //  替换Token
                Request.Builder builder = request.newBuilder();
                retryRequest(builder, token);
                response = chain.proceed(builder.build());
                return response;
            }

        } catch (Exception e) {
            throw new IOException(e);
        }

        return response;
    }

    // 拦截请求
    protected Request onInterceptRequest(Request request) {
        return request;
    }


    protected String bufferBody(Response response) {
        try {
            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.forName("UTF-8");
            return buffer.clone().readString(charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查TOKEN是否过期
     *
     * @param request  请求
     * @param response 响应
     * @param body     服务器返回的数据
     * @return 过期返true
     * @throws Exception
     */
    protected abstract boolean invalidateToken(Request request, Response response, String body) throws Exception;

    /**
     * 重新刷新TOKEN
     *
     * @param body 服务器返回的数据
     * @return 新的Token
     */
    protected abstract String refreshToken(String body) throws IOException;

    /**
     * 得到新的Token之后重新发请求
     *
     * @param builder 请求体
     * @param token   新的Token
     */
    protected abstract void retryRequest(Request.Builder builder, String token) throws IOException;
}
