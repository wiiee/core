package com.wiiee.core.sample.domain.entity;

import com.wiiee.core.platform.data.BaseData;

public class User extends BaseData<String> {
    //Id为工号

    public String password;

    public String name;

    public User(){

    }

    public User(String id, String password, String name) {
        super(id);
        this.password = password;
        this.name = name;
    }
}
