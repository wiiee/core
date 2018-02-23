package com.wiiee.core.platform.context;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

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
    HttpMethod getHttpMethod();

    //json request/response数据
    String getRequest();
    String getResponse();

    void setRequest(Object request);
    void setResponse(Object response);
}