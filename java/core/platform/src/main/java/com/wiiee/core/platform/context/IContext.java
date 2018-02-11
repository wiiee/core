package com.wiiee.core.platform.context;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface IContext {
    //当前用户Id
    String getUserId();
    String getRequestSessionId();
    String getSessionId();
    String getUri();
    String getRemoteIp();

    //json request/response数据
    Object getRequest();
    Object getResponse();

    void setRequest(Object request);
    void setResponse(Object response);
}