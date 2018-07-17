package com.wiiee.core.platform.log;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Jest {
    private final Logger _logger = LoggerFactory.getLogger(Jest.class);

    private JestClient jestClient;

    public Jest(JestClient jestClient) {
        this.jestClient = jestClient;
    }

    public void log(LogItem item) {
        try {
            String indexName = "core-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            Index index = new Index.Builder(item).index(indexName).type(item.type).build();
            jestClient.execute(index);
        } catch (Exception ex) {
            _logger.error(ex.getMessage());
        }
    }
}
