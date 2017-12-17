package com.platform.context;

/**
 * Created by wiiee on 9/9/2017.
 */
public abstract class BaseContext implements IContext {
    private String userId;
    private String uuid;
    private String uri;
    private String remoteIp;

    public BaseContext(String userId, String uuid, String uri, String remoteIp) {
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
