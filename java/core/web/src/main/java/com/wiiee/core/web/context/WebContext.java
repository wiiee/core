package com.wiiee.core.web.context;

import com.wiiee.core.platform.context.IContext;

/**
 * Created by wang.na on 2017/03/20.
 */
public class WebContext implements IContext {
    private String userId;
    private String uuid;
    private String uri;
    private String remoteIp;

    public WebContext(String userId, String uuid, String uri, String remoteIp) {
        this.userId = userId;
        this.uuid = uuid;
        this.uri = uri;
        this.remoteIp = remoteIp;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getRemoteIp() {
        return remoteIp;
    }
}
