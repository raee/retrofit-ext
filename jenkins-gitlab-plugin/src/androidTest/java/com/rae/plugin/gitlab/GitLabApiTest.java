/*
 * Copyright (c) 2017.
 */

package com.rae.plugin.gitlab;

import com.github.raee.runit.AndroidRUnit4ClassRunner;
import com.github.raee.runit.RUnitTestLogUtils;
import com.rae.plugin.gitlab.api.IProductsApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * api test
 * Created by ChenRui on 2017/6/20 0020 16:49.
 */
@RunWith(AndroidRUnit4ClassRunner.class)
public class GitLabApiTest {

    IProductsApi mProductsApi;

    public String getMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (element.getMethodName().contains("test")) {
                return element.getMethodName();
            }
        }

        return "test";
    }

    private <T> void runTest(Observable<T> observable) {

        final String methodName = getMethodName();


        observable.subscribeOn(Schedulers.io()).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                AndroidRUnit4ClassRunner.finish(methodName);
            }
        }).subscribe(new DefaultObserver<T>() {
            @Override
            public void onNext(T t) {
                RUnitTestLogUtils.print("gitlab", t);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Before
    public void setup() {
//        mProductsApi = GitLabApi.create(IProductsApi.class);
    }


    JenkinsGitLabPlugin plugin = new JenkinsGitLabPlugin(GitLabJenkinsUrls.GITLAB_URL, GitLabJenkinsUrls.GITLAB_PRIVATE_TOKEN, GitLabJenkinsUrls.JENKINS_URL, GitLabJenkinsUrls.JENKINS_PROJECT);

    @Test
    public void testProducts() {
        runTest(mProductsApi.products());
    }

    @Test
    public void testCommits() {
        runTest(mProductsApi.commits("267", null));
    }

    @Test
    public void testCommit() {
        runTest(plugin.afterCommits("Android", null, "c80adb2745fd3b736a88c4a1a7ef1ac608aa9ac9"));
    }

    @Test
    public void testJenkins() {
        runTest(plugin.getJenkinsLastSuccessBuild());
    }

    @Test
    public void testCheckUpdate() {
        runTest(plugin.checkUpdate("Android", null, "3af29749563105cd6587c7ef532201749adfd26c"));
    }


}
