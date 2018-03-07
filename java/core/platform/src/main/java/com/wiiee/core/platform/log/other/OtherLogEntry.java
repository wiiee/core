package com.wiiee.core.platform.log.other;

import com.wiiee.core.platform.log.ILogEntry;

public class OtherLogEntry implements ILogEntry {
    public String type;
    public Exception exception;
    public Object data;

    public OtherLogEntry(){
        this.type = "Other";
    }

    public OtherLogEntry build(Exception ex, Object data){
        this.exception = ex;
        this.data = data;
        return this;
    }

    @Override
    public String getType() {
        return type;
    }
}
