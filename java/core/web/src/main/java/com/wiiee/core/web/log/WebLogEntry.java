package com.wiiee.core.web.log;

import com.wiiee.core.platform.context.IContext;
import com.wiiee.core.platform.log.BaseLogEntry;
import com.wiiee.core.web.context.HttpRequestInfo;
import com.wiiee.core.web.context.WebContext;

public class WebLogEntry extends BaseLogEntry {
    public HttpRequestInfo httpRequestInfo;

    @Override
    public BaseLogEntry build(IContext context, String className, String methodName, int errorCode, String errorMsg, long elapsed_milliseconds, Object request, Object response) {
        if(context instanceof WebContext){
            this.httpRequestInfo = ((WebContext) context).httpRequestInfo;
        }

        return super.build(context, className, methodName, errorCode, errorMsg, elapsed_milliseconds, request, response);
    }
}