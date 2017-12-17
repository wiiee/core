package com.dataaccess.repository;

import com.platform.data.History;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wiiee on 9/1/2017.
 */
public interface HistoryRepository extends MongoRepository<History, String> {

}
