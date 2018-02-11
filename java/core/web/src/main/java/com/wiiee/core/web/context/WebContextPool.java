package com.wiiee.core.web.context;

import com.wiiee.core.platform.util.ObjectPool;
import org.springframework.stereotype.Component;

@Component
public class WebContextPool extends ObjectPool<WebContext> {
    public WebContextPool() {
        super(WebContext.class, 256);
    }
}
