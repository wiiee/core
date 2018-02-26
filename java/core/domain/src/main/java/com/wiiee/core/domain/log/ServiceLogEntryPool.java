package com.wiiee.core.domain.log;

import com.wiiee.core.platform.util.ObjectPool;
import org.springframework.stereotype.Component;

@Component
public class ServiceLogEntryPool extends ObjectPool<ServiceLogEntry> {
    public ServiceLogEntryPool() {
        super(0);
    }
}
