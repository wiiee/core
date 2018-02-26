package com.wiiee.core.platform.log.other;

import com.wiiee.core.platform.util.ObjectPool;
import org.springframework.stereotype.Component;

@Component
public class OtherLogEntryPool extends ObjectPool<OtherLogEntry> {
    public OtherLogEntryPool() {
        super(0);
    }
}
