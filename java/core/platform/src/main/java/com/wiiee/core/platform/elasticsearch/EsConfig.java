package com.wiiee.core.platform.elasticsearch;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.wiiee.core.platform.elasticsearch.repository")
public class EsConfig {
}
