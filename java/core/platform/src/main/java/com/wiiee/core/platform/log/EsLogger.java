package com.wiiee.core.platform.log;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
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
    private JestClient jestClient;

    @Override
    public boolean log(ILogEntry entry) {
        if(entry instanceof LogEntry && jestClient != null){
            try {
                Index index = new Index.Builder(entry).index("core").type("log").build();
                jestClient.execute(index);
            }
            catch (Exception ex){
                _logger.error(ex.getMessage());
            }
        }

        return true;
    }
}
