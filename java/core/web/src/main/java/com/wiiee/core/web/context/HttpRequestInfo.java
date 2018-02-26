package com.wiiee.core.web.context;

import org.springframework.http.HttpMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestInfo {
    public String requestUri;
    public String authType;
    public String remoteUser;
    public String remoteHost;
    public String remoteAddr;
    public String requestSessionId;
    public HttpMethod httpMethod;
    public Cookie[] cookies;
    public Map<String, String> httpHeaders;

    public HttpRequestInfo() {
    }

    public HttpRequestInfo(HttpServletRequest request) {
        this.requestSessionId = request.getRequestedSessionId();
        this.authType = request.getAuthType();
        this.remoteUser = request.getRemoteUser();
        this.requestUri = request.getRequestURI();
        this.remoteHost = request.getRemoteHost();
        this.remoteAddr = request.getRemoteAddr();
        this.cookies = request.getCookies();
        this.httpHeaders = buildHttpHeaders(request);
        this.httpMethod = HttpMethod.resolve(request.getMethod());
    }

    private Map<String, String> buildHttpHeaders(HttpServletRequest httpServletRequest) {
        Map<String, String> headers = new HashMap<>();

        Enumeration<String> names = httpServletRequest.getHeaderNames();

        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headers.put(name, httpServletRequest.getHeader(name));
        }

        return headers;
    }
}
