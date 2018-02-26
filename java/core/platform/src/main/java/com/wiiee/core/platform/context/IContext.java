package com.wiiee.core.platform.context;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface IContext {
    //当前用户Id
    String getUserId();
    String getSessionId();

//    String getUri();
//    String getRemoteIp();
//    HttpMethod getHttpMethod();
//    String getRequestSessionId();

    //request/response对象
    Object getRequest();
    Object getResponse();

    void setRequest(Object request);
    void setResponse(Object response);
}