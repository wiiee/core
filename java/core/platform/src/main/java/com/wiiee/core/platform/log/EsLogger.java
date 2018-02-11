package com.wiiee.core.platform.log;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by wiiee on 9/10/2017.
 */
@Component
public class EsLogger implements ILogger {
    private static final Logger _logger = LoggerFactory.getLogger(EsLogger.class);

    @Autowired
    private JestClient jestClient;

    @Autowired
    private LogEntryPool logEntryPool;

    @Override
    public boolean log(ILogEntry entry) {
        if (entry instanceof LogEntry && jestClient != null) {
            try {
                String indexName = "core-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                Index index = new Index.Builder(entry).index(indexName).type("log").build();
                jestClient.execute(index);
            } catch (Exception ex) {
                _logger.error(ex.getMessage());
            } finally {
                logEntryPool.free((LogEntry) entry);
            }
        }

        return true;
    }
}
