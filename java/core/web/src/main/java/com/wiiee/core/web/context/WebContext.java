package com.wiiee.core.web.context;

import com.wiiee.core.platform.context.IContext;
import com.wiiee.core.platform.util.GsonUtil;
import org.springframework.http.HttpMethod;

/**
 * Created by wang.na on 2017/03/20.
 */
public class WebContext implements IContext {
    private String userId;
    private String requestSessionId;
    private String sessionId;
    private String uri;
    private String remoteIp;
    private HttpMethod httpMethod;

    private String request;
    private String response;

    public WebContext() {
    }

    public WebContext build(String userId, String requestSessionId, String sessionId, String uri, String remoteIp, HttpMethod httpMethod) {
        this.userId = userId;
        this.requestSessionId = requestSessionId;
        this.sessionId = sessionId;
        this.uri = uri;
        this.remoteIp = remoteIp;
        this.httpMethod = httpMethod;

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
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @Override
    public String getRequest() {
        return request;
    }

    @Override
    public String getResponse() {
        return response;
    }

    @Override
    public void setRequest(Object request) {
        this.request = GsonUtil.toJson(request);
    }

    @Override
    public void setResponse(Object response) {
        this.response = GsonUtil.toJson(response);
    }
}
