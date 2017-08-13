/*
 * Copyright (c) 2017.
 */

package com.rae.swift;

import android.text.TextUtils;

import java.util.Collection;

/**
 * 工具类
 * Created by ChenRui on 2017/5/5 0005 12:02.
 */
public final class Rx {

    public static <E> boolean isEmpty(Collection<E> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

}
