package com.wiiee.core.domain.service;

import com.wiiee.core.platform.constant.HistoryType;
import com.wiiee.core.platform.context.IContext;
import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.data.HistoryInfo;
import com.wiiee.core.platform.data.IData;
import com.wiiee.core.platform.log.LogItem;
import com.wiiee.core.platform.log.LogSender;
import com.wiiee.core.platform.util.GsonUtil;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by wang.na on 2016/11/7.
 */
public abstract class BaseService<T extends IData<Id>, Id extends Serializable> implements IService {
    private MongoRepository<T, Id> repository;

    private static IContextRepository _contextRepository;
    private static LogSender _logSender;
    private static CacheManager _cacheManager;

    private Class<T> entityClazz;

    public BaseService(MongoRepository<T, Id> repository, Class<T> entityClazz) {
        this.repository = repository;
        this.entityClazz = entityClazz;
    }

    public void _setContextRepository(IContextRepository contextRepository) {
        _contextRepository = contextRepository;
    }

    public void _setLogSender(LogSender logSender) {
        _logSender = logSender;
    }

    public void _setCacheManager(CacheManager cacheManager) {
        _cacheManager = cacheManager;
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

    private Cache getCache() {
        return _cacheManager.getCache(this.getClass().getSimpleName());
    }

    private void putCacheEntry(Id id, T entity){
        Cache cache = getCache();

        if(cache != null){
            cache.put(id, entity);
        }
    }

    private void evictCacheEntry(Id id){
        Cache cache = getCache();

        if(cache != null){
            cache.evict(id);
        }
    }

    private T getCacheEntry(Id id) {
        Cache cache = getCache();

        if (cache != null) {
            Object entry = cache.get(id);

            if (entry != null && this.getClass().isInstance(entry)) {
                return (T) entry;
            }
        }

        return null;
    }

    public T get(Id id) {
        T entry = getCacheEntry(id);
        return entry != null ? entry : repository.findOne(id);
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
        T result = repository.insert(entity);

        if (!isHistoryService()) {
            _logSender.send(buildLogItem(buildId(entity.getId()), GsonUtil.toJson(entity), HistoryType.Create));
        }

        putCacheEntry(result.getId(), result);

        return result;
    }

    public List<T> create(Iterable<T> entities) {
        List<T> result = repository.insert(entities);

        if (!isHistoryService()) {
            for (T entity : entities) {
                _logSender.send(buildLogItem(buildId(entity.getId()), GsonUtil.toJson(entity), HistoryType.Create));
            }
        }

        for (T entity : result) {
            putCacheEntry(entity.getId(), entity);
        }

        return result;
    }

    public void update(T entity) {
        repository.save(entity);

        if (!isHistoryService()) {
            _logSender.send(buildLogItem(buildId(entity.getId()), GsonUtil.toJson(entity), HistoryType.Update));
        }

        putCacheEntry(entity.getId(), entity);
    }

    public void update(Iterable<T> entities) {
        repository.save(entities);

        if (!isHistoryService()) {
            for (T entity : entities) {
                _logSender.send(buildLogItem(buildId(entity.getId()), GsonUtil.toJson(entity), HistoryType.Update));
            }
        }

        for (T entity : entities) {
            putCacheEntry(entity.getId(), entity);
        }
    }

    public void delete(Id id) {
        repository.delete(id);

        if (!isHistoryService()) {
            _logSender.send(buildLogItem(buildId(id), null, HistoryType.Delete));
        }

        evictCacheEntry(id);
    }

    public void delete(Iterable<T> entities) {
        repository.delete(entities);

        for (T entity : entities) {
            if (!isHistoryService()) {
                _logSender.send(buildLogItem(buildId(entity.getId()), null, HistoryType.Delete));
            }

            evictCacheEntry(entity.getId());
        }
    }

    private String buildId(Id id) {
        return entityClazz.getName() + "_" + id;
    }

    private LogItem buildLogItem(String id, String data, HistoryType type) {
        return new LogItem(id, new HistoryInfo(data, getContext().getUserId(), LocalDateTime.now(), type));
    }

    private boolean isHistoryService() {
        return this instanceof HistoryService;
    }
}
