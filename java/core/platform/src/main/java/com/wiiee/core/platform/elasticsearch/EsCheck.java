package com.wiiee.core.platform.elasticsearch;

import com.wiiee.core.platform.log.LogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

@Component
public class EsCheck implements CommandLineRunner {
    private static final Logger _logger = LoggerFactory.getLogger(EsCheck.class);

    private ElasticsearchTemplate esTemplate;

    public EsCheck(ElasticsearchTemplate esTemplate) {
        this.esTemplate = esTemplate;
    }

    @Override
    public void run(String... strings) throws Exception {
        _logger.info("\n\n" + "=========================================================\n"
                + "Checking index starting..." + "\n"
                + "=========================================================\n\n");

        try{
            if (!esTemplate.indexExists(LogEntry.class)) {
                esTemplate.createIndex(LogEntry.class);
                esTemplate.putMapping(LogEntry.class);
                esTemplate.refresh(LogEntry.class);
            }
        }
        catch (Exception ex)
        {
            _logger.error(ex.getMessage());
        }

        _logger.info("\n\n" + "=========================================================\n"
                + "Checking index done..." + "\n"
                + "=========================================================\n\n");
    }
}
