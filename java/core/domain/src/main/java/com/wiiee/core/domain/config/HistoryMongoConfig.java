package com.wiiee.core.domain.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.wiiee.core.domain.repository")
public class HistoryMongoConfig extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "history";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient();
    }
}
