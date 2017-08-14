/*
 * Copyright (c) 2017.
 */

package com.rae.plugin.gitlab.model;

import java.util.List;

/**
 * Created by ChenRui on 2017/7/11 0011 15:34.
 */
public class JenkinsJobInfo {


    private List<JenkinsJobActionInfo> actions;

    public List<JenkinsJobActionInfo> getActions() {
        return actions;
    }

    public void setActions(List<JenkinsJobActionInfo> actions) {
        this.actions = actions;
    }
}
