package com.wiiee.core.platform.log.other;

import com.wiiee.core.platform.log.ILogEntry;

public class OtherLogEntry implements ILogEntry {
    public String type;
    public Exception exception;

    public OtherLogEntry(){
        this.type = "Other";
    }

    public OtherLogEntry build(Exception ex){
        this.exception = ex;
        return this;
    }

    @Override
    public String getType() {
        return type;
    }
}
