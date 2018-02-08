package com.wiiee.core.platform.log;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoggerChain implements ApplicationContextAware {
    private List<ILogger> loggers;

    public LoggerChain() {
        this.loggers = new ArrayList<>();
    }

    //ToDo: use queue in future
    public void log(ILogEntry entry) {
        for (ILogger logger : loggers) {
            if(!logger.log(entry)){
                return;
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansOfType(ILogger.class).forEach((k, v) -> this.loggers.add(v));
    }
}