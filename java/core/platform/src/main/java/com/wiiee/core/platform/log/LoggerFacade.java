package com.wiiee.core.platform.log;

import com.wiiee.core.platform.util.ObjectPool;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LoggerFacade {
    private final Logger _logger = LoggerFactory.getLogger(LoggerFacade.class);

    private JestClient jestClient;

    public LoggerFacade(JestClient jestClient) {
        this.jestClient = jestClient;
    }

    //ToDo: use queue in future
    public void log(ILogEntry entry, ObjectPool pool) {
        if(this.jestClient != null){
            try {
                String indexName = "core-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                Index index = new Index.Builder(entry).index(indexName).type(entry.getType()).build();
                jestClient.execute(index);
            } catch (Exception ex) {
                _logger.error(ex.getMessage());
            } finally {
                if(pool != null){
                    pool.free(entry);
                }
            }
        }
    }
}