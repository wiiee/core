package com.wiiee.core.domain.service;

/**
 * Created by wiiee on 9/17/2017.
 */
public class ServiceResult {
    public boolean isSuccessful;
    public String message;
    public Object data;

    public ServiceResult(boolean isSuccessful, String message, Object data) {
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.data = data;
    }
}
