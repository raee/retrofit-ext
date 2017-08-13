package com.squareup.okhttp3.interceptor;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 日志记录拦截器
 * 如果你的项目为library，可以在android{}节点添加下面的配置，可以防止设置BuildConfig.Debug为false
 * defaultPublishConfig "debug"
 * Created by ChenRui on 2017/4/25 0025 16:02.
 */
public class LoggerInterceptor implements Interceptor {

    private String mTag;

    public LoggerInterceptor(String tag) {
        mTag = tag;
    }

    public LoggerInterceptor() {
        this("HTTP-LOG");
    }

    private void log(String msg, Object... args) {
        String text = String.format(msg, args);
        // 超出大小
        int maxLength = 2048;
        if (text.length() > maxLength) {
            for (int i = 0; i < text.length(); i += maxLength) {
                int len = i + maxLength;
                if (len < text.length())
                    Log.d(mTag, text.substring(i, len));
                else
                    Log.d(mTag, text.substring(i, text.length()));
            }
        } else {
            Log.d(mTag, text);
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        try {
            log("==============================================================================");
            log("%s %s", request.method(), request.url());

            String headString = request.headers().toString();
            if (!TextUtils.isEmpty(headString)) {
                log("请求头：\n%s", headString);
                log("\n");
            }

            String requestBody = bufferRequestBody(request);
            if (requestBody != null) {
                log("%s", requestBody);
            }

//        headString = response.headers().toString();
//        if (!TextUtils.isEmpty(headString)) {
//            log("响应头：\n%s", headString);
//            log("\n");
//        }

            if (response.code() < 200 || response.code() >= 300) {
                log("请求错误：%d %s", response.code(), response.message());
            } else {
                String body = bufferBody(response);
                if (TextUtils.isEmpty(body)) {
                    log("返回结果为空！");
                } else {
                    log("返回结果：\n%s", decodeUnicode(body));
                }
            }
        } catch (Throwable e) {
            // 不要影响正常的返回
            Log.e(mTag, "LoggerInterceptor打印日志错误！");
        }

        return response;
    }

    private String bufferBody(Response response) {
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

    private String bufferRequestBody(Request request) {
        try {
            if (request.body() == null || request.body().contentLength() <= 0) {
                return null;
            }

            Buffer sink;
            sink = new Buffer();
            request.body().writeTo(sink);
            return sink.readString(Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuilder outBuffer = new StringBuilder(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

}
