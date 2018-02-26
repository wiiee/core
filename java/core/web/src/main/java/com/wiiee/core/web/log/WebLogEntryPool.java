package com.wiiee.core.web.log;

import com.wiiee.core.platform.util.ObjectPool;
import org.springframework.stereotype.Component;

@Component
public class WebLogEntryPool extends ObjectPool<WebLogEntry> {
    public WebLogEntryPool() {
        super(0);
    }
}
