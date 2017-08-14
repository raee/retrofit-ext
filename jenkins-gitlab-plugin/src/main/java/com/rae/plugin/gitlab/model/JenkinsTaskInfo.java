/*
 * Copyright (c) 2017.
 */

package com.rae.plugin.gitlab.model;

/**
 * Created by ChenRui on 2017/7/11 0011 15:20.
 */
public class JenkinsTaskInfo {

    private JenkinsBuildInfo lastSuccessfulBuild;

    public JenkinsBuildInfo getLastSuccessfulBuild() {
        return lastSuccessfulBuild;
    }

    public void setLastSuccessfulBuild(JenkinsBuildInfo lastSuccessfulBuild) {
        this.lastSuccessfulBuild = lastSuccessfulBuild;
    }
}
