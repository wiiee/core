package com.wiiee.core.platform.log;

import com.google.gson.annotations.SerializedName;
import com.wiiee.core.platform.context.IContext;
import com.wiiee.core.platform.util.GsonUtil;

import java.time.Instant;

public class LogItem {
    public String id;

    //用户Id，sessionId
    public String userId;
    public String sessionId;

    //类、方法
    public String className;
    public String methodName;

    //错误码、消息
    public int errorCode;
    public String errorMsg;

    //执行时间
    public long elapsed_milliseconds;

    //Request,Response
    public String request;
    public String response;

    public Object other;

    public String type;

    //时间戳
    @SerializedName("@timestamp")
    public String timestamp;

    public LogItem(){
        this.timestamp = Instant.now().toString();
    }

    public LogItem build(IContext context, String className, String methodName, int errorCode, String errorMsg, long elapsed_milliseconds, Object request, Object response, Object other) {
        this.userId = context == null ? null : context.getUserId();
        this.sessionId = context == null ? null : context.getSessionId();

        this.className = className;
        this.methodName = methodName;

        this.errorCode = errorCode;
        this.errorMsg = errorMsg;

        this.elapsed_milliseconds = elapsed_milliseconds;

        this.request = GsonUtil.toJson(request);
        this.response = GsonUtil.toJson(response);

        this.other = other;

        this.type = "default";

        return this;
    }
}
