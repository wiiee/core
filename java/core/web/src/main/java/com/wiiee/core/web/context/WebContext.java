package com.wiiee.core.web.context;

import com.wiiee.core.platform.context.IContext;

/**
 * Created by wang.na on 2017/03/20.
 */
public class WebContext implements IContext {
    private String userId;
    private String requestSessionId;
    private String sessionId;
    private String uri;
    private String remoteIp;

    private Object request;
    private Object response;

    public WebContext() {
    }

    public WebContext build(String userId, String requestSessionId, String sessionId, String uri, String remoteIp) {
        this.userId = userId;
        this.requestSessionId = requestSessionId;
        this.sessionId = sessionId;
        this.uri = uri;
        this.remoteIp = remoteIp;

        return this;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getRequestSessionId() {
        return requestSessionId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getRemoteIp() {
        return remoteIp;
    }

    @Override
    public Object getRequest() {
        return request;
    }

    @Override
    public Object getResponse() {
        return response;
    }

    @Override
    public void setRequest(Object request) {
        this.request = request;
    }

    @Override
    public void setResponse(Object response) {
        this.response = response;
    }
}
