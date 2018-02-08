package com.wiiee.core.platform.log;

import com.wiiee.core.platform.elasticsearch.repository.LogEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wiiee on 9/10/2017.
 */
@Component
public class EsLogger implements ILogger {
    private static final Logger _logger = LoggerFactory.getLogger(EsLogger.class);

    @Autowired
    private LogEntryRepository logEntryRepository;

    @Override
    public boolean log(ILogEntry entry) {
        if(entry instanceof LogEntry){
            try {
                logEntryRepository.save((LogEntry) entry);
            }
            catch (Exception ex){
                _logger.error(ex.getMessage());
            }
        }

        return true;
    }
}
