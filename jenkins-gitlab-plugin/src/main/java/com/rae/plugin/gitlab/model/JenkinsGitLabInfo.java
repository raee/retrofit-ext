/*
 * Copyright (c) 2017.
 */

package com.rae.plugin.gitlab.model;

import java.util.List;

/**
 * Created by ChenRui on 2017/7/11 0011 17:09.
 */
public class JenkinsGitLabInfo {
    private List<Commit> mCommits;
    private JenkinsJobBuildVisionInfo mBuildVisionInfo;

    public List<Commit> getCommits() {
        return mCommits;
    }

    public void setCommits(List<Commit> commits) {
        mCommits = commits;
    }

    public JenkinsJobBuildVisionInfo getBuildVisionInfo() {
        return mBuildVisionInfo;
    }

    public void setBuildVisionInfo(JenkinsJobBuildVisionInfo buildVisionInfo) {
        mBuildVisionInfo = buildVisionInfo;
    }
}
