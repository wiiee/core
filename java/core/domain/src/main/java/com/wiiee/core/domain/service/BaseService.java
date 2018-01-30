package com.wiiee.core.domain.service;

import com.wiiee.core.platform.constant.HistoryType;
import com.wiiee.core.platform.context.IContext;
import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.data.IData;
import com.wiiee.core.platform.data.HistoryInfo;
import com.wiiee.core.platform.log.LogItem;
import com.wiiee.core.platform.log.LogSender;
import com.wiiee.core.platform.util.GsonUtil;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wang.na on 2016/11/7.
 */
public abstract class BaseService<T extends IData<Id>, Id extends Serializable> implements IService {
    private MongoRepository<T, Id> repository;

    private static IContextRepository _contextRepository;
    private static LogSender _logSender;

    public BaseService(MongoRepository<T, Id> repository) {
        this.repository = repository;
    }

    public void _setContextRepository(IContextRepository contextRepository){
        _contextRepository = contextRepository;
    }

    public void _setLogSender(LogSender logSender){
        _logSender = logSender;
    }

    @Override
    public IContext getContext() {
        try {
            return _contextRepository.getCurrent();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<T> get() {
        return repository.findAll();
    }

    public T get(Id id) {
        return repository.findOne(id);
    }

    public List<T> get(Sort sort) {
        return repository.findAll(sort);
    }

    public T getOne(Example<T> example) {
        return repository.findOne(example);
    }

    public List<T> getAll(Example<T> example) {
        return repository.findAll(example);
    }

    public List<T> get(Example<T> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    public T create(T entity) {
        if(!isHistoryService()){
            _logSender.send(buildLogItem(buildId(entity.getId()), GsonUtil.toJson(entity), HistoryType.Create));
        }

        return repository.insert(entity);
    }

    public List<T> create(Iterable<T> entities) {
        if(!isHistoryService()){
            for (T entity : entities) {
                _logSender.send(buildLogItem(buildId(entity.getId()), GsonUtil.toJson(entity), HistoryType.Create));
            }
        }

        return repository.insert(entities);
    }

    public void update(T entity) {
        if(!isHistoryService()){
            _logSender.send(buildLogItem(buildId(entity.getId()), GsonUtil.toJson(entity), HistoryType.Update));
        }

        repository.save(entity);
    }

    public void update(Iterable<T> entities) {
        if(!isHistoryService()){
            for (T entity : entities) {
                _logSender.send(buildLogItem(buildId(entity.getId()), GsonUtil.toJson(entity), HistoryType.Update));
            }
        }

        repository.save(entities);
    }

    public void delete(Id id) {
        if(!isHistoryService()){
            _logSender.send(buildLogItem(buildId(id), null, HistoryType.Delete));
        }

        repository.delete(id);
    }

    public void delete(Iterable<T> entities) {
        for (T entity : entities) {
            if(!isHistoryService()){
                _logSender.send(buildLogItem(buildId(entity.getId()), null, HistoryType.Delete));
            }

            this.delete(entity.getId());
        }
    }

    private String buildId(Id id) {
        return this.getClass().getName() + "_" + id;
    }

    private LogItem buildLogItem(String id, String data, HistoryType type){
        return new LogItem(id, new HistoryInfo(data, getContext().getUserId(), new Date(), type));
    }

    private boolean isHistoryService(){
        return this instanceof HistoryService;
    }
}
