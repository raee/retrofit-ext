/*
 * Copyright (c) 2017.
 */

package com.rae.plugin.gitlab.model;

/**
 * Created by ChenRui on 2017/7/11 0011 15:21.
 */
public class JenkinsBuildInfo {

    /**
     * _class : hudson.model.FreeStyleBuild
     * number : 56
     * url : http://localhost:8088/job/trade/56/
     */

    private String _class;
    private int number;
    private String url;

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
