/*
 * Copyright (c) 2017.
 */

package com.rae.plugin.gitlab.model;

/**
 * Created by ChenRui on 2017/7/11 0011 15:45.
 */
public class JenkinsJobActionInfo {
    private String _class;
    private JenkinsJobBuildVisionInfo lastBuiltRevision;

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public JenkinsJobBuildVisionInfo getLastBuiltRevision() {
        return lastBuiltRevision;
    }

    public void setLastBuiltRevision(JenkinsJobBuildVisionInfo lastBuiltRevision) {
        this.lastBuiltRevision = lastBuiltRevision;
    }
}
