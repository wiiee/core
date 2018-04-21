package com.wiiee.core.platform.log;

import com.wiiee.core.platform.util.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerFacade {
    private final Logger _logger = LoggerFactory.getLogger(LoggerFacade.class);

//    private ElasticsearchTemplate template;
//
//    public LoggerFacade(ElasticsearchTemplate template) {
//        this.template= template;
//    }

    //ToDo: use queue in future
    public void log(ILogEntry entry, ObjectPool pool) {

    }

//    public void log(ILogEntry entry, ObjectPool pool) {
//        if(this.template != null){
//            try {
//                String indexName = "core-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
//                Index index = new Index.Builder(entry).index(indexName).type(entry.getType()).build();
//                jestClient.execute(index);
//            } catch (Exception ex) {
//                _logger.error(ex.getMessage());
//            } finally {
//                if(pool != null){
//                    pool.free(entry);
//                }
//            }
//        }
//    }
}