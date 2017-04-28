package com.rae.session;

/**
 * Created by ChenRui on 2017/4/28 0028 17:48.
 */
public class UserInfo implements SessionParcelableConverter {
    private String name;
    private String userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserInfo() {
    }

    @Override
    public String toString() {
        return name;
    }


}
