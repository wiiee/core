package com.wiiee.core.platform.log;

import com.wiiee.core.platform.property.AppProperties;
import com.wiiee.core.platform.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by wiiee on 9/10/2017.
 */
@Component
public class EsLogger implements ILogger {
    private final RestTemplate restTemplate;

    @Autowired
    public EsLogger(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Autowired
    private AppProperties appProperties;

    @Override
    public void log(ILogEntry entry) {
        if(entry instanceof EsLogEntry){
            System.out.println(GsonUtil.toJson(entry));
            System.out.println(appProperties.getEsUrl());
        }
    }
}
