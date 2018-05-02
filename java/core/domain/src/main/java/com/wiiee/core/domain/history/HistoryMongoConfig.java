package com.wiiee.core.domain.history;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.wiiee.core.domain.history.repository",
        mongoTemplateRef = "historyMongoTemplate")
public class HistoryMongoConfig extends AbstractMongoConfiguration {
    private MongoProperties mongoProperties;

    @Autowired
    public HistoryMongoConfig(MongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }

    @Override
    protected String getDatabaseName() {
        return "history";
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(new MongoClientURI(mongoProperties.determineUri()));
    }

    @Bean
    @Primary
    public MongoTemplate historyMongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }


}
