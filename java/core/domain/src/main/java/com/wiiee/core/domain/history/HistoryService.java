package com.wiiee.core.domain.history;

import com.wiiee.core.domain.service.BaseService;
import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.history.History;
import com.wiiee.core.platform.history.HistoryLogItem;
import com.wiiee.core.platform.history.IHistoryService;
import com.wiiee.core.platform.log.LogWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by wiiee on 9/1/2017.
 */
@Component
public class HistoryService extends BaseService<History, String> implements IHistoryService {
    private static final Logger _logger = LoggerFactory.getLogger(HistoryService.class);

    @Autowired
    private IContextRepository contextRepository;

    @Autowired(required = false)
    private CacheManager cacheManager;

    @Autowired
    private LogWriter logWriter;

    public HistoryService(MongoRepository<History, String> repository) {
        super(repository);
    }

    @PostConstruct
    public void init(){
        _setContextRepository(contextRepository);
        _setHistoryService(this);
        _setCacheManager(cacheManager);
        _setLoggerChain(logWriter);
    }

    @Override
    public void process(HistoryLogItem item) {
        try{
            switch (item.getHistoryInfo().type) {
                case Create:
                    create(new History(item.getId(), item.getHistoryInfo()));
                    break;
                case Update:
                case Delete:
                    History history = (History) get(item.getId()).data;
                    history.addHistoryInfo(item.getHistoryInfo());
                    update(history);
                    break;
                default:
                    break;
            }
        }
        catch (Exception ex){
            _logger.error(ex.getMessage());
        }
    }
}
