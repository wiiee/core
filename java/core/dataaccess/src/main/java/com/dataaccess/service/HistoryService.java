package com.dataaccess.service;

import com.platform.context.IContextRepository;
import com.platform.data.History;
import com.platform.data.IHistoryService;
import com.platform.log.LogItem;
import com.platform.log.LogSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LogSender logSender;

    @Autowired
    public HistoryService(MongoRepository<History, String> repository) {
        super(repository);
    }

    @PostConstruct
    public void init(){
        _setContextRepository(contextRepository);
        _setLogSender(logSender);
    }

    @Override
    public synchronized void process(LogItem item) {
        try{
            switch (item.getHistoryInfo().getType()) {
                case Create:
                    create(new History(item.getId(), item.getHistoryInfo()));
                    break;
                case Update:
                case Delete:
                    History history = get(item.getId());
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
