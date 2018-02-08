package com.wiiee.core.platform.log;

public interface ILogger {
    //是否继续往下log
    boolean log(ILogEntry entry);
}
