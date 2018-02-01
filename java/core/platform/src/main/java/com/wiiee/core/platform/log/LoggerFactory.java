package com.wiiee.core.platform.log;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoggerFactory implements ApplicationContextAware {
    private List<ILogger> loggers;

    public LoggerFactory() {
        this.loggers = new ArrayList<>();
    }

    //ToDo: use queue in future
    public void log() {
        for (ILogger logger : loggers) {
            logger.log();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansOfType(ILogger.class).forEach((k, v) -> this.loggers.add(v));
    }
}