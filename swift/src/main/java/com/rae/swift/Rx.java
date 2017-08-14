/*
 * Copyright (c) 2017.
 */

package com.rae.swift;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.Collection;

/**
 * 工具类
 * Created by ChenRui on 2017/5/5 0005 12:02.
 */
public final class Rx {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat();

    public static <E> boolean isEmpty(Collection<E> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    public static <E> int getCount(Collection<E> list) {
        if (list == null) return 0;
        return list.size();
    }

    public static int parseInt(CharSequence text, int defaultValue) {
        if (TextUtils.isEmpty(text)) return defaultValue;
        try {
            return Integer.parseInt(text.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static int parseInt(CharSequence text) {
        return parseInt(text, 0);
    }

    public static double parseDouble(CharSequence text, double defaultValue) {
        if (TextUtils.isEmpty(text)) return defaultValue;
        try {
            return Double.parseDouble(text.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static double parseDouble(CharSequence text) {
        return parseDouble(text, 0);
    }

    public static float parseFloat(CharSequence text, float defaultValue) {
        if (TextUtils.isEmpty(text)) return defaultValue;
        try {
            return Float.parseFloat(text.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static float parseFloat(CharSequence text) {
        return parseFloat(text, 0);
    }

    public static long parseLong(CharSequence text, long defaultValue) {
        if (TextUtils.isEmpty(text)) return defaultValue;
        try {
            return Long.parseLong(text.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static long parseLong(CharSequence text) {
        return parseLong(text, 0l);
    }

    /**
     * 格式化标准的金额，保留小数点后两位
     *
     * @param value 金额
     */
    public static String formatPrice(double value) {
        DECIMAL_FORMAT.applyPattern("###,###,##0.00");
        return DECIMAL_FORMAT.format(value);
    }

    /**
     * 格式化数字
     *
     * @param pattern 格式
     * @param value   数值
     */
    public static String formatValue(String pattern, double value) {
        DECIMAL_FORMAT.applyPattern(pattern);
        return DECIMAL_FORMAT.format(value);
    }


    /**
     * 格式化为百分比
     *
     * @param value 数值
     */
    public static String formatPercent(double value) {
        DECIMAL_FORMAT.applyPattern("0.00%");
        return DECIMAL_FORMAT.format(value);
    }


    /**
     * 屏蔽手机号敏感信息
     */
    public static String blockingPhone(String text) {
        if (TextUtils.isEmpty(text) || text.length() < 11) return text;
        char[] chars = text.toCharArray();

        int start = 2;
        int blockLen = start + 4;

        for (int i = 0; i < chars.length; i++) {
            if (i > start && i <= blockLen) {
                chars[i] = '*';
            }
        }
        return new String(chars);
    }
}
