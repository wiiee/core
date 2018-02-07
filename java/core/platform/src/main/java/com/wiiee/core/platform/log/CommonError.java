package com.wiiee.core.platform.log;

/**
 * Created by wiiee on 2/7/2018.
 */
public enum CommonError {
    NoError(0),
    ServiceError(1);

    private int errorCode;

    CommonError(int errorCode){
        this.errorCode = errorCode;
    }

    public int value(){
        return errorCode;
    }
}
