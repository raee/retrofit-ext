/*
 * Copyright (c) 2017.
 */

package com.rae.plugin.gitlab.api;

import com.rae.plugin.gitlab.model.JenkinsJobInfo;
import com.rae.plugin.gitlab.model.JenkinsTaskInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Jenkins Task API
 * Created by ChenRui on 2017/7/11 0011 9:37.
 */
public interface IJenkinsTaskApi {

    @GET("http://{address}/job/{project}/api/json?pretty=true")
    Observable<JenkinsTaskInfo> getTaskInfo(@Path("address") String address, @Path("project") String project);


    @GET("http://{address}/job/{project}/{num}/api/json?pretty=true")
    Observable<JenkinsJobInfo> getJobInfo(@Path(value = "address") String address, @Path("project") String project, @Path("num") int number);

}
