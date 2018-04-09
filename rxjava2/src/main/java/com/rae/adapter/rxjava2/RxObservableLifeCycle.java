/*
 * Copyright (c) 2018.
 */

package com.rae.adapter.rxjava2;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * RxJava Life Cycle
 * Created by ChenRui on 2018/1/31 0031 14:26.
 */
public final class RxObservableLifeCycle implements Application.ActivityLifecycleCallbacks {

    private final static Map<String, List<Disposable>> sObservableDisposableList = new WeakHashMap<>();
    private final static String DEFAULT_TAG = "--DEFAULT-TAG--";
    private final static String TAG = "RxObservableLifeCycle";
    public final static String NONE_DEFAULT_TAG = "NONE_TAG";
    private final static RxObservableLifeCycle INSTANCES = new RxObservableLifeCycle();
    private static String CURRENT_ACTIVITY_TAG = DEFAULT_TAG;
    public static boolean DEBUG = false;

    private RxObservableLifeCycle() {
    }

    public static Observable put(Observable observable) {
        if (observable == null) return null;
        return observable
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        // 对请求分类
                        List<Disposable> disposables = sObservableDisposableList.get(CURRENT_ACTIVITY_TAG);
                        if (disposables == null) {
                            disposables = new ArrayList<>();
                            sObservableDisposableList.put(CURRENT_ACTIVITY_TAG, disposables);
                        }
                        debugLog("put tag is:" + CURRENT_ACTIVITY_TAG + "; tag list size is: " + disposables.size());
                        sObservableDisposableList.get(CURRENT_ACTIVITY_TAG).add(disposable);
                    }
                });
    }

    public static void dispose() {
        dispose(DEFAULT_TAG);
    }

    /**
     * 释放指定TAG的请求
     *
     * @param tag 标签
     */
    public static void dispose(String tag) {
        try {
            if (TextUtils.isEmpty(tag) || !sObservableDisposableList.containsKey(tag)) {
                debugLog("dispose method just return; tag is :" + tag);
                return;
            }
            List<Disposable> disposables = sObservableDisposableList.get(tag);
            for (Disposable disposable : disposables) {
                if (disposable != null && !disposable.isDisposed())
                    disposable.dispose();
            }
            debugLog("dispose tag is :" + tag + "; size is: " + disposables.size());
            disposables.clear();
            sObservableDisposableList.remove(tag);
        } catch (Exception e) {
            Log.e("RxObservableLifeCycle", "释放HTTP请求失败！", e);
        } finally {
            sObservableDisposableList.remove(tag);
        }
    }

    private static void debugLog(String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    public static void register(Application application) {
        application.registerActivityLifecycleCallbacks(INSTANCES);
        debugLog("register application life cycle callbacks");
    }

    public static void unregister(Application application) {
        application.unregisterActivityLifecycleCallbacks(INSTANCES);
        debugLog("unregister application life cycle callbacks");
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        CURRENT_ACTIVITY_TAG = activity.getLocalClassName();
        debugLog("activity resumed, current tag is : " + CURRENT_ACTIVITY_TAG);
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        CURRENT_ACTIVITY_TAG = null;
        debugLog("activity stop! current tag is null!");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        CURRENT_ACTIVITY_TAG = null;
        debugLog("activity destroyed: " + activity.getLocalClassName());
        dispose(activity.getLocalClassName());
        dispose();
    }
}
