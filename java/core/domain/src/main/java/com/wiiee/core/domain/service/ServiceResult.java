package com.wiiee.core.domain.service;

import com.wiiee.core.platform.data.IData;

import java.util.List;

/**
 * Created by wiiee on 9/17/2017.
 */
public class ServiceResult<T extends IData> {
    public boolean isSuccessful;

    public int errorCode;
    public String errorMsg;

    public T data;
    public List<T> datum;

    public static final ServiceResult SUCCESS = new ServiceResult();

    public static final ServiceResult INVALID_USERNAME = new ServiceResult(100, "Id is invalid, please check it.");
    public static final ServiceResult INVALID_PWD = new ServiceResult(101, "Password is invalid, please check it.");
    public static final ServiceResult INVALID_USERNAME_OR_PWD = new ServiceResult(102, "Id or password is invalid, please check it.");
    public static final ServiceResult USER_ALREADY_EXIST = new ServiceResult(103, "User is already exist.");


    //返回ok
    private ServiceResult(){
        this.isSuccessful = true;
    }

    //返回ok和数据
    public ServiceResult(T data) {
        this.isSuccessful = true;
        this.data = data;
    }

    //返回ok和数据集
    public ServiceResult(List<T> datum) {
        this.isSuccessful = true;
        this.datum = datum;
    }

    //返回错误的errorCode和errorMessage
    public ServiceResult(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
