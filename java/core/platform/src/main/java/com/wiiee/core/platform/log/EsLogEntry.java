package com.wiiee.core.platform.log;

import com.wiiee.core.platform.util.GsonUtil;

/**
 * Created by wiiee on 9/10/2017.
 */
public class EsLogEntry implements ILogEntry {
    public String groupName;
    public String className;
    public int errorCode;
    public String errorMsg;

    public String data;

    public long duration;

    public EsLogEntry(long duration) {
        this.errorCode = -1;
        this.duration = duration;
    }

    public EsLogEntry(String groupName, String className, int errorCode, String errorMsg, Object data, long duration) {
        this.groupName = groupName;
        this.className = className;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = GsonUtil.toJson(data);
        this.duration = duration;
    }
}