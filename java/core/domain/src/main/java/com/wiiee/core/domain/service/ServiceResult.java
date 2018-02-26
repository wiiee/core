package com.wiiee.core.domain.service;

import com.wiiee.core.platform.data.IData;
import com.wiiee.core.platform.exception.MyException;

import java.util.HashMap;
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

    public static final ServiceResult SUCCESS = new ServiceResult(true, 0, null, null, null);

    public ServiceResult(boolean isSuccessful, int errorCode, String errorMsg, T data, List<T> datum) {
        this.isSuccessful = isSuccessful;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
        this.datum = datum;
    }

    public static ServiceResult getByException(MyException myException){
        if(!_serviceResults.containsKey(myException.errorCode)){
            _serviceResults.put(myException.errorCode, new ServiceResult(false, myException.errorCode, myException.getMessage(), null, null));
        }

        return _serviceResults.get(myException.errorCode);
    }
}
