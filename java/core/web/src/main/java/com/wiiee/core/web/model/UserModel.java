package com.wiiee.core.web.model;

/**
 * Created by wiiee on 9/17/2017.
 */
public class UserModel {
    private String id;
    private String password;

    public UserModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
