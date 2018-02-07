package com.wiiee.core.domain.service;

import com.wiiee.core.platform.data.IData;

import java.util.List;

/**
 * Created by wiiee on 9/17/2017.
 */
public class ServiceResult<T extends IData> {
    public boolean isSuccessful;
    public String message;
    public T data;
    public List<T> datum;

    public static ServiceResult SUCCESS = new ServiceResult();

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

    //返回错误的message
    public ServiceResult(String message) {
        this.message = message;
    }
}
