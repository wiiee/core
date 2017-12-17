package com.platform.context;

/**
 * Created by wang.na on 2016/11/7.
 */
public interface IContext {
    String getUserId();
    String getUuid();
    String getUri();
    String getRemoteIp();
}