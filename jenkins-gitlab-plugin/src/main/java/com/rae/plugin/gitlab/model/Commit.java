/*
 * Copyright (c) 2017.
 */

package com.rae.plugin.gitlab.model;

/**
 * Created by ChenRui on 2017/6/20 0020 17:51.
 */
public class Commit {

    /**
     * id : 5702f055ac7685e772486544d7380c4c3bba2041
     * short_id : 5702f055
     * title : 解决BUG：
     * author_name : rae
     * author_email : 798404374@qq.com
     * created_at : 2017-06-20T14:02:45.000+08:00
     * message : 解决BUG：
     注册页面，手机号码格式不正确的时候，没有提示语的问题
     委托售出，持仓不足时，去融货弹窗问题
     转出页面，输入小数时，点击转出，显示的金额错误（转入页面也是如此）
     开户后，点击融资报错的问题
     注册页面，注册成功后弹出选择会员框的问题
     我的-账户余额-账户余额页面title问题

     */

    private String id;
    private String short_id;
    private String title;
    private String author_name;
    private String author_email;
    private String created_at;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShort_id() {
        return short_id;
    }

    public void setShort_id(String short_id) {
        this.short_id = short_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
