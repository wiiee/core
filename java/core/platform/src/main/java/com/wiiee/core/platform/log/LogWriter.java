package com.wiiee.core.platform.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogWriter {
    private final Logger _logger = LoggerFactory.getLogger(LogWriter.class);

    private Jest jest;

    public LogWriter(Jest jest){
        this.jest = jest;
    }

    //ToDo: use queue in future
    public void log(LogItem item) {
        jest.log(item);
    }
}