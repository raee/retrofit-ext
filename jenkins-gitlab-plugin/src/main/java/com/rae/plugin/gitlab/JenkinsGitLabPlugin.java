/*
 * Copyright (c) 2017.
 */

package com.rae.plugin.gitlab;

import android.text.TextUtils;
import android.util.Log;

import com.rae.plugin.gitlab.api.IJenkinsTaskApi;
import com.rae.plugin.gitlab.api.IProductsApi;
import com.rae.plugin.gitlab.model.Commit;
import com.rae.plugin.gitlab.model.JenkinsGitLabInfo;
import com.rae.plugin.gitlab.model.JenkinsJobActionInfo;
import com.rae.plugin.gitlab.model.JenkinsJobBuildVisionInfo;
import com.rae.plugin.gitlab.model.JenkinsJobInfo;
import com.rae.plugin.gitlab.model.JenkinsTaskInfo;
import com.rae.plugin.gitlab.model.Product;
import com.squareup.okhttp3.OkHttpExtBuilder;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * jenkins gitlab auto update plugin
 * Created by ChenRui on 2017/6/20 0020 16:26.
 */
public class JenkinsGitLabPlugin {

    private final Retrofit mRetrofit;
    private IProductsApi mProductsApi;
    private IJenkinsTaskApi mJenkinsTaskApi;

    /**
     * @param gitLabUrl    example: http://gitlab.com/
     * @param privateToken account privateToken
     */
    public JenkinsGitLabPlugin(String gitLabUrl, String privateToken, String jenkinsUrl, String jenkinsProject) {

        GitLabJenkinsUrls.GITLAB_URL = gitLabUrl + "api/v3/";
        GitLabJenkinsUrls.GITLAB_PRIVATE_TOKEN = privateToken;

        GitLabJenkinsUrls.JENKINS_URL = jenkinsUrl;
        GitLabJenkinsUrls.JENKINS_PROJECT = jenkinsProject;

        OkHttpClient httpClient = new OkHttpExtBuilder()
                .https()
                .debug("gitlab")
                .build()
                .addInterceptor(new RequestInterceptor())
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.100.169", 8888)))
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(GitLabJenkinsUrls.GITLAB_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        mProductsApi = mRetrofit.create(IProductsApi.class);
        mJenkinsTaskApi = mRetrofit.create(IJenkinsTaskApi.class);
    }


    /**
     * 获取该版本之后的提交记录
     *
     * @param commitId 提交ID
     */
    public Observable<List<Commit>> afterCommits(final String productName, final String branch, final String commitId) {
        return mProductsApi.products()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<Product>, Product>() {
                    @Override
                    public Product apply(@NonNull List<Product> products) throws Exception {
                        for (Product product : products) {
                            if (TextUtils.equals(productName, product.getName())) {
                                return product;
                            }
                        }

                        throw new NullPointerException("not product " + productName);
                    }
                })
                .flatMap(new Function<Product, ObservableSource<List<Commit>>>() {
                    @Override
                    public ObservableSource<List<Commit>> apply(@NonNull Product product) throws Exception {
                        return mProductsApi.commits(String.valueOf(product.getId()), branch);
                    }
                })
                // 获取大于这个ID的记录
                .map(new Function<List<Commit>, List<Commit>>() {
                    @Override
                    public List<Commit> apply(@NonNull List<Commit> commits) throws Exception {
                        int index = 0;
                        int count = commits.size();
                        for (int i = 0; i < count; i++) {
                            Commit commit = commits.get(i);
                            if (TextUtils.equals(commitId, commit.getId())) {
                                index = i;
                                break;
                            }
                        }

                        if (index > 0) {
                            return commits.subList(0, index);
                        }
                        if (commitId != null) return Collections.emptyList();

                        return commits;
                    }
                });
    }


    public Observable<JenkinsJobBuildVisionInfo> getJenkinsLastSuccessBuild() {


        return mJenkinsTaskApi.getTaskInfo(GitLabJenkinsUrls.JENKINS_URL, GitLabJenkinsUrls.JENKINS_PROJECT)
                .flatMap(new Function<JenkinsTaskInfo, ObservableSource<JenkinsJobInfo>>() {
                    @Override
                    public ObservableSource<JenkinsJobInfo> apply(@NonNull JenkinsTaskInfo jenkinsTaskInfo) throws Exception {
                        if (jenkinsTaskInfo.getLastSuccessfulBuild() == null) {
                            throw new NullPointerException("没有找到最后成功构建的版本！");
                        }
                        return mJenkinsTaskApi.getJobInfo(GitLabJenkinsUrls.JENKINS_URL, GitLabJenkinsUrls.JENKINS_PROJECT, jenkinsTaskInfo.getLastSuccessfulBuild().getNumber());
                    }
                })
                .flatMap(new Function<JenkinsJobInfo, ObservableSource<JenkinsJobBuildVisionInfo>>() {
                    @Override
                    public ObservableSource<JenkinsJobBuildVisionInfo> apply(@NonNull JenkinsJobInfo jenkinsJobInfo) throws Exception {
                        for (JenkinsJobActionInfo info : jenkinsJobInfo.getActions()) {
                            Log.i("rae", "action = " + info.get_class());
                            if (info.getLastBuiltRevision() != null) {
                                Log.i("rae", "version = " + info.getLastBuiltRevision().getSHA1());
                                return Observable.just(info.getLastBuiltRevision());
                            }
                        }
                        return null;
                    }
                });
    }


    public Observable<List<Commit>> checkUpdate(final String productName, final String branch, final String commitId) {
        return afterCommits(productName, branch, commitId)
                .flatMap(new Function<List<Commit>, ObservableSource<JenkinsGitLabInfo>>() {
                    @Override
                    public ObservableSource<JenkinsGitLabInfo> apply(@NonNull final List<Commit> commits) throws Exception {
                        return getJenkinsLastSuccessBuild().map(new Function<JenkinsJobBuildVisionInfo, JenkinsGitLabInfo>() {
                            @Override
                            public JenkinsGitLabInfo apply(@NonNull JenkinsJobBuildVisionInfo buildVisionInfo) throws Exception {
                                JenkinsGitLabInfo info = new JenkinsGitLabInfo();
                                info.setCommits(commits);
                                info.setBuildVisionInfo(buildVisionInfo);
                                return info;
                            }
                        });
                    }
                })
                .map(new Function<JenkinsGitLabInfo, List<Commit>>() {
                    @Override
                    public List<Commit> apply(@NonNull JenkinsGitLabInfo info) throws Exception {
                        JenkinsJobBuildVisionInfo buildVisionInfo = info.getBuildVisionInfo();
                        List<Commit> commits = info.getCommits();

                        if (commits.size() <= 0) {
                            throw new NullPointerException("没有获取到Git提交信息");
                        }
                        if (buildVisionInfo == null) {
                            throw new NullPointerException("没有获取到构建信息");
                        }

                        String lastCommit = commits.get(0).getId();
                        String buildVersion = buildVisionInfo.getSHA1();

                        if (lastCommit.equalsIgnoreCase(buildVersion))
                            return commits;

                        throw new NullPointerException(String.format("没有找到更新，Git=%s, Jenkins=%s", lastCommit, buildVersion));
                    }
                });


    }
}
