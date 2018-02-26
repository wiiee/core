package com.wiiee.core.domain.service;

import com.wiiee.core.platform.data.IData;
import com.wiiee.core.platform.exception.MyException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wiiee on 9/17/2017.
 */
public class ServiceResult<T extends IData> {
    public boolean isSuccessful;

    public int errorCode;
    public String errorMsg;

    public T data;
    public List<T> datum;

    private static Map<Integer, ServiceResult> _serviceResults;

    static {
        _serviceResults = new ConcurrentHashMap<>();
    }

    public static final ServiceResult SUCCESS = new ServiceResult();

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

    public static ServiceResult getByException(MyException myException){
        if(!_serviceResults.containsKey(myException.errorCode)){
            _serviceResults.put(myException.errorCode, new ServiceResult(myException.errorCode, myException.getMessage()));
        }

        return _serviceResults.get(myException.errorCode);
    }
}
