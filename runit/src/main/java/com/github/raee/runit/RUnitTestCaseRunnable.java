package com.github.raee.runit;

import android.text.TextUtils;
import android.util.Log;

import com.github.raee.runit.argument.ArgumentElement;

import org.junit.runners.model.FrameworkMethod;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 用于测试队列的测试方法
 * Created by ChenRui on 2016/12/22 0022 9:54.
 */
public abstract class RUnitTestCaseRunnable<T> implements Runnable, RUnitArgumentTestListener<T> {

    private static final String TAG = "rae-test-case";
    private final List<Throwable> mErrors;
    private CountDownLatch mCountDownLatch = new CountDownLatch(1);
    ArgumentElement mArgumentRule; // 当前的测试方法规则
    private FrameworkMethod mTestMethod; // 测试方法

    public RUnitTestCaseRunnable(ArgumentElement rule, List<Throwable> errors) {
        mArgumentRule = rule;
        mErrors = errors;
    }

    public RUnitTestCaseRunnable() {
        mErrors = null;
    }

    protected void setCountDownLatch(CountDownLatch countDownLatch) {
        mCountDownLatch = countDownLatch;
    }


    /**
     * API 测试成功调用该接口
     *
     * @param data 数据
     */
    @Override
    public void onApiSuccess(T data) {
        notifyApiSuccess(data);
    }

    protected void notifyApiSuccess(T data) {
        if (data instanceof Void) {
            Log.i(TAG, testName() + " 接口请求成功，返回结果为Void类型");
        } else {
            Log.i(TAG, testName() + "接口请求成功，返回结果为：" + data);
        }
        if (data == null) {
            notifyError(new NullPointerException(testName() + " 接口请求成功，但接口返回结果为空"));
        }
        if (mArgumentRule != null && !TextUtils.isEmpty(mArgumentRule.successMessage())) {
            notifyError(new RUnitTestError(String.format("%s %s %s", testName(), mArgumentRule.successMessage(), argumentString())));
        }
        RUnitTestLogUtils.print(TAG, data);
        finish();
    }

    public void setTestMethod(FrameworkMethod testMethod) {
        mTestMethod = testMethod;
    }

    protected String argumentString() {
        if (mArgumentRule == null || mArgumentRule.value().length <= 0) return "";
        StringBuilder sb = new StringBuilder();

        for (String s : mArgumentRule.value()) {
            sb.append(s);
            sb.append("; ");
        }
        return sb.toString();
    }

    @Override
    public void onApiError(int error, String msg) {
        if (mArgumentRule == null || TextUtils.isEmpty(mArgumentRule.successMessage())) {
            notifyError(new RUnitTestError(String.format(Locale.getDefault(), testName() + " 接口返回失败：错误码[%d]，%s %s，测试参数信息：%s", error, msg, errorMessage(), argumentString())));
        } else {
            Log.i(TAG, testName() + " 接口返回失败，但因为设置了接口调用成功的提示信息，故通过测试。接口返回信息：" + msg);
        }
        finish();
    }

    protected String errorMessage() {
        return mArgumentRule == null ? "" : mArgumentRule.errorMessage();
    }

    @Override
    public void finish() {
        mCountDownLatch.countDown();
    }

    /**
     * 等待线程结束
     */
    protected void await() {
        try {
            mCountDownLatch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            notifyError(new RUnitTestRuntimeException(testName() + " 执行测试方法30秒超时", e));
        }
    }

    protected String testName() {
        if (mArgumentRule == null || TextUtils.isEmpty(mArgumentRule.name())) {
            return mTestMethod == null ? "" : mTestMethod.getName();
        }
        return String.format("【%s】", mArgumentRule.name());
    }

    protected void notifyError(Throwable e) {
        if (mErrors != null) {
            mErrors.add(e);
        } else {
            throw new RUnitTestRuntimeException(e);
        }
    }
}
