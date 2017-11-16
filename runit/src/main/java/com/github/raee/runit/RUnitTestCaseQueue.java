package com.github.raee.runit;

import android.app.Instrumentation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 测试用例队列，按照{@link #put(RUnitTestCaseRunnable)} 顺序一个个的压入。
 * 执行完一个才执行下一个。
 * Created by ChenRui on 2016/12/22 0022 9:35.
 */
public class RUnitTestCaseQueue {

    private final Queue<RUnitTestCaseRunnable> mRunnableStack = new LinkedList<>(); // 测试队列
    private Instrumentation mInstrumentation;
    private boolean mIsAsyncTask; // 是否为异步执行

    public RUnitTestCaseQueue(Instrumentation instrumentation) {
        mInstrumentation = instrumentation;
    }

    public RUnitTestCaseQueue(Instrumentation instrumentation, boolean isAsyncTask) {
        mInstrumentation = instrumentation;
        mIsAsyncTask = isAsyncTask;
    }

    public void put(RUnitTestCaseRunnable runnable) {
        mRunnableStack.add(runnable);
    }

    public void start() {
        CountDownLatch countDownLatch = new CountDownLatch(mRunnableStack.size());

        while (!mRunnableStack.isEmpty()) {
            RUnitTestCaseRunnable runnable = mRunnableStack.poll();
            if (mIsAsyncTask) {
                runnable.setCountDownLatch(countDownLatch);
                mInstrumentation.runOnMainSync(runnable);
            } else {
                mInstrumentation.runOnMainSync(runnable);
                runnable.await();
            }
        }

        if (mIsAsyncTask) {
            try {
                countDownLatch.await(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RUnitTestRuntimeException("测试超时！", e);
            }
        }

    }

}
