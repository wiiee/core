package com.wiiee.core.platform.log;

import com.wiiee.core.platform.context.IContext;
import com.wiiee.core.platform.util.GsonUtil;

import java.sql.Timestamp;

/**
 * Created by wiiee on 9/10/2017.
 */

public class LogEntry implements ILogEntry {
    public String context_userId;
    public String context_requestSessionId;
    public String context_sessionId;
    public String context_uri;
    public String context_remoteIp;

    //事件类型
    public String eventName;

    //类别、方法
    public String className;
    public String methodName;

    //错误消息
    public int errorCode;
    public String errorMsg;

    //请求、响应
    public String req;
    public String res;

    //执行时间
    public long elapsed_milliseconds;

    //数据
    public String data;

    //时间戳
    public Timestamp timestamp;
    
    public LogEntry(){}

    public LogEntry(IContext context,
                    String eventName,
                    String className, String methodName,
                    int errorCode, String errorMsg,
                    Object req, Object res,
                    long elapsed_milliseconds,
                    Object data) {
        this.context_userId = context.getUserId();
        this.context_requestSessionId = context.getRequestSessionId();
        this.context_sessionId = context.getSessionId();
        this.context_uri = context.getUri();
        this.context_remoteIp = context.getRemoteIp();

        this.eventName = eventName;

        this.className = className;
        this.methodName = methodName;

        this.errorCode = errorCode;
        this.errorMsg = errorMsg;

        this.req = GsonUtil.toJson(req);
        this.res = GsonUtil.toJson(res);

        this.elapsed_milliseconds = elapsed_milliseconds;

        this.data = GsonUtil.toJson(data);

        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public LogEntry build(IContext context,
                      String eventName,
                      String className, String methodName,
                      int errorCode, String errorMsg,
                      Object req, Object res,
                      long elapsed_milliseconds,
                      Object data) {
        this.context_userId = context.getUserId();
        this.context_requestSessionId = context.getRequestSessionId();
        this.context_sessionId = context.getSessionId();
        this.context_uri = context.getUri();
        this.context_remoteIp = context.getRemoteIp();

        this.eventName = eventName;

        this.className = className;
        this.methodName = methodName;

        this.errorCode = errorCode;
        this.errorMsg = errorMsg;

        this.req = GsonUtil.toJson(req);
        this.res = GsonUtil.toJson(res);

        this.elapsed_milliseconds = elapsed_milliseconds;

        this.data = GsonUtil.toJson(data);

        this.timestamp = new Timestamp(System.currentTimeMillis());

        return this;
    }
}