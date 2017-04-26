package com.squareup.okhttp3.utils;

import android.net.Uri;
import android.text.TextUtils;

import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 签名工具
 * Created by ChenRui on 2017/4/25 0025 15:24.
 */
public final class SignUtils {

    public static final String KEY_MAC_HMAC_MD5 = "HmacMD5";
    public static final String KEY_MAC_HMAC_SHA1 = "HmacSHA1";
    public static final String KEY_MAC_HMAC_SHA256 = "HmacSHA256";
    public static final String KEY_MAC_HMAC_SHA384 = "HmacSHA384";
    public static final String KEY_MAC_HMAC_SHA512 = "HmacSHA512";

//    private static final String ALGORITHM = "RSA";

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

//    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String DEFAULT_CHARSET = "UTF-8";

    // 默认HMAC 算法
    private static final String DEFAULT_HMAC = KEY_MAC_HMAC_SHA256;


    /**
     * byte to hex
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * HMAC加密
     *
     * @param KEY_MAC 算法类型，参考常量KEY_MAC_*
     * @param key     私钥
     * @param content 加密内容
     * @return
     * @throws Exception
     */
    public static String encryptHMAC(String KEY_MAC, String key, String content) throws Exception {
        Mac mac = Mac.getInstance(KEY_MAC);
        SecretKey secretKey = new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), mac.getAlgorithm());
        mac.init(secretKey);
        byte[] data = mac.doFinal(content.getBytes(DEFAULT_CHARSET));
        return bytesToHex(data);
    }

    /**
     * 默认HMAC加密
     *
     * @param key     私钥
     * @param content 加密内容
     * @return
     */
    public static String encryptHMAC(String key, String content) {
        try {
            return encryptHMAC(DEFAULT_HMAC, key, content);
        } catch (Exception ex) {
            return content;
        }
    }

    /**
     * 按照红黑树（Red-Black tree）的 NavigableMap 实现
     * 按照字母大小排序
     */
    public static Map<String, String> sort(Map<String, String> map) {
        if (map == null) return null;
        Map<String, String> result = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        result.putAll(map);
        return result;
    }


    /**
     * 组合参数
     *
     * @param map
     * @return 如：key1Value1Key2Value2....
     */
    public static String groupStringParam(Map<String, String> map) {
        if (map == null) return null;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> item : map.entrySet()) {

            if (TextUtils.isEmpty(item.getValue())) {
                continue;
            }

            sb.append(item.getKey());
            sb.append(item.getValue());
        }
        return sb.toString();
    }

    /**
     * 转成URL 参数
     *
     * @param map
     * @return
     */
    public static String toStringParams(Map<String, String> map) {
        return toStringParams(map, false);
    }

    /**
     * 转成URL 参数
     *
     * @param map
     * @return 如：key1=value1&key2=value2
     */
    public static String toStringParams(Map<String, String> map, boolean enableUrlEncode) {
        Uri.Builder builder = new Uri.Builder();
        for (Map.Entry<String, String> item : map.entrySet()) {

            if (TextUtils.isEmpty(item.getKey()) || TextUtils.isEmpty(item.getValue()))
                continue;

            String value = item.getValue();
            if (enableUrlEncode) {
                value = URLEncoder.encode(value);
            }
            builder.appendQueryParameter(item.getKey(), value);
        }
        return builder.build().getQuery();
    }

    /**
     * 对请求参数进行签名，返回sign参数。
     *
     * @param url    请求URL
     * @param secret APP secret
     * @param map    请求参数
     * @return sign字符串
     */
    public static String sign(String url, String secret, Map<String, String> map) {
        if (map == null) return null;

        // 1、对data参数进行重新排序
        Map<String, String> sortMap = sort(map);

        // 2、拼接参数：key1Value1key2Value2
        String urlParams = groupStringParam(sortMap);

        // 3、拼接准备排序： stringURI + stringParams + AppSecret
        String signString = url + urlParams + secret;


        // 4、私钥签名
        String sign = encryptHMAC(secret, signString);
        if (!TextUtils.isEmpty(sign)) {
            sign = sign.toLowerCase();
        }

        return sign;
    }
}
