package com.wiiee.core.platform.log;

import com.wiiee.core.platform.util.ObjectPool;
import org.springframework.stereotype.Component;

@Component
public class LogEntryPool extends ObjectPool<LogEntry> {
    public LogEntryPool() {
        super(LogEntry.class);
    }
}
