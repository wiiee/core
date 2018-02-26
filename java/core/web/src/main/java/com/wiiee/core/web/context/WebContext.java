package com.wiiee.core.web.context;

import com.wiiee.core.domain.security.SecurityUtil;
import com.wiiee.core.platform.context.IContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by wang.na on 2017/03/20.
 */
public class WebContext implements IContext {
    private String userId;
    private String sessionId;

    private Object request;
    private Object response;

    public HttpRequestInfo httpRequestInfo;

    public WebContext() {

    }

    public WebContext build(HttpServletRequest httpServletRequest) {
        if(httpServletRequest != null){
            HttpSession httpSession = httpServletRequest.getSession();

            this.userId = SecurityUtil.getUserId();
            this.sessionId = httpSession != null ? httpSession.getId() : null;

            this.httpRequestInfo = new HttpRequestInfo(httpServletRequest);
        }

        return this;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
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
