package com.wiiee.core.platform.log;

import com.wiiee.core.platform.context.IContext;
import com.wiiee.core.platform.util.GsonUtil;
import org.elasticsearch.common.Strings;
import org.springframework.data.elasticsearch.annotations.Document;


/**
 * Created by wiiee on 9/10/2017.
 */

@Document(indexName = "core", type = "logs")
public class LogEntry implements ILogEntry {
    public String id;

    public IContext context;

    public String groupName;
    public String className;
    public String methodName;

    public int errorCode;
    public String errorMsg;
    public long duration;

    public String data;

    public LogEntry(IContext context, String groupName, String className, String methodName, int errorCode, String errorMsg, long duration, Object data) {
        this.id = Strings.base64UUID();
        this.context = context;
        this.groupName = groupName;
        this.className = className;
        this.methodName = methodName;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.duration = duration;
        this.data = GsonUtil.toJson(data);
    }
}