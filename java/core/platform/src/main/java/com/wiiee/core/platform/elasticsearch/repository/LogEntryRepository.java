package com.wiiee.core.platform.elasticsearch.repository;

import com.wiiee.core.platform.log.LogEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogEntryRepository extends ElasticsearchRepository<LogEntry, String> {
}
