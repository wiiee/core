package com.wiiee.core.domain.service;

import com.wiiee.core.domain.history.HistoryService;
import com.wiiee.core.platform.constant.HistoryType;
import com.wiiee.core.platform.context.IContext;
import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.data.IData;
import com.wiiee.core.platform.exception.CoreException;
import com.wiiee.core.platform.history.HistoryInfo;
import com.wiiee.core.platform.history.HistoryLogItem;
import com.wiiee.core.platform.history.IHistoryService;
import com.wiiee.core.platform.log.LoggerFacade;
import com.wiiee.core.platform.util.GsonUtil;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by wang.na on 2016/11/7.
 */
public abstract class BaseService<T extends IData<Id>, Id extends Serializable> implements IService {
    private MongoRepository<T, Id> repository;

    private static IContextRepository _contextRepository;
    private static CacheManager _cacheManager;
    private static IHistoryService _historyService;
    private static LoggerFacade _loggerFacade;

    private Class<T> type;

    public BaseService(MongoRepository<T, Id> repository) {
        this.repository = repository;
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected void _setContextRepository(IContextRepository contextRepository) {
        _contextRepository = contextRepository;
    }

    protected void _setCacheManager(CacheManager cacheManager) {
        _cacheManager = cacheManager;
    }

    protected void _setHistoryService(IHistoryService historyService) {
        _historyService = historyService;
    }

    protected void _setLoggerChain(LoggerFacade loggerFacade) {
        _loggerFacade = loggerFacade;
    }

    @Override
    public IContext getContext() {
        try {
            return _contextRepository.getCurrent();
        } catch (Exception ex) {
            return null;
        }
    }

    private Cache getCache() {
        return _cacheManager == null ? null : _cacheManager.getCache(this.getClass().getSimpleName());
    }

    private void putCacheEntry(Id id, T entity) {
        Cache cache = getCache();

        if (cache != null) {
            cache.put(id, entity);
        }
    }

    private void evictCacheEntry(Id id) {
        Cache cache = getCache();

        if (cache != null) {
            cache.evict(id);
        }
    }

    private T getCacheEntry(Id id) {
        Cache cache = getCache();

        if (cache != null) {
            Object entry = cache.get(id);

            if (entry != null && type.isInstance(entry)) {
                return (T) entry;
            }
        }

        return null;
    }

    public <R extends MongoRepository<T, Id>> R getRepository(Class<R> clazz) {
        if (clazz.isInstance(repository)) {
            return (R) repository;
        }

        return null;
    }

    public ServiceResult<T> get() {
        try {
            return new ServiceResult<>(repository.findAll());
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    public ServiceResult<T> get(Id id) {
        if(id == null){
            return ServiceResult.getByException(CoreException.EXCEPTION_NULL_PARAMETERS);
        }

        try {
            T entry = getCacheEntry(id);
            return new ServiceResult<>(entry != null ? entry : repository.findOne(id));
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    public ServiceResult<T> get(Sort sort) {
        try {
            return new ServiceResult<>(repository.findAll(sort));
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    public ServiceResult<T> getOne(Example<T> example) {
        try {
            return new ServiceResult<>(repository.findOne(example));
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    public ServiceResult<T> getAll(Example<T> example) {
        try {
            return new ServiceResult<>(repository.findAll(example));
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    public ServiceResult<T> get(Example<T> example, Sort sort) {
        try {
            return new ServiceResult<>(repository.findAll(example, sort));
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    public ServiceResult<T> create(T entity) {
        if(entity == null){
            return ServiceResult.getByException(CoreException.EXCEPTION_NULL_PARAMETERS);
        }

        if(!entity.isValid()){
            return ServiceResult.getByException(CoreException.EXCEPTION_INVALID_DATA);
        }

        try {
            T result = repository.insert(entity);

            if (!isHistoryService()) {
                _historyService.process(buildLogItem(buildId(entity.getId()), GsonUtil.toJson(entity), HistoryType.Create));
            }

            putCacheEntry(result.getId(), result);

            return new ServiceResult<>(result);
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    //ToDo: 检查数据有效性
    public ServiceResult<T> create(Iterable<T> entities) {
        try {
            List<T> result = repository.insert(entities);

            if (!isHistoryService()) {
                for (T entity : entities) {
                    _historyService.process(buildLogItem(buildId(entity.getId()), GsonUtil.toJson(entity), HistoryType.Create));
                }
            }

            for (T entity : result) {
                putCacheEntry(entity.getId(), entity);
            }

            return new ServiceResult<>(result);
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    public ServiceResult<T> update(T entity) {
        if(entity == null){
            return ServiceResult.getByException(CoreException.EXCEPTION_NULL_PARAMETERS);
        }

        try {
            repository.save(entity);

            if (!isHistoryService()) {
                _historyService.process(buildLogItem(buildId(entity.getId()), GsonUtil.toJson(entity), HistoryType.Update));
            }

            putCacheEntry(entity.getId(), entity);

            return ServiceResult.SUCCESS;
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    //ToDo: 检查数据有效性
    public ServiceResult<T> update(Iterable<T> entities) {
        try {
            repository.save(entities);

            if (!isHistoryService()) {
                for (T entity : entities) {
                    _historyService.process(buildLogItem(buildId(entity.getId()), GsonUtil.toJson(entity), HistoryType.Update));
                }
            }

            for (T entity : entities) {
                putCacheEntry(entity.getId(), entity);
            }

            return ServiceResult.SUCCESS;
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    public ServiceResult<T> delete(Id id) {
        if(id == null){
            return ServiceResult.getByException(CoreException.EXCEPTION_NULL_PARAMETERS);
        }

        try {
            repository.delete(id);

            if (!isHistoryService()) {
                _historyService.process(buildLogItem(buildId(id), null, HistoryType.Delete));
            }

            evictCacheEntry(id);

            return ServiceResult.SUCCESS;
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    //ToDo: 检查数据有效性
    public ServiceResult<T> delete(Iterable<T> entities) {
        try {
            repository.delete(entities);

            for (T entity : entities) {
                if (!isHistoryService()) {
                    _historyService.process(buildLogItem(buildId(entity.getId()), null, HistoryType.Delete));
                }

                evictCacheEntry(entity.getId());
            }

            return ServiceResult.SUCCESS;
        } catch (Exception ex) {
            return new ServiceResult<>(CoreException.EXCEPTION_SERVICE.errorCode, ex.getMessage());
        }
    }

    private String buildId(Id id) {
        return type.getName() + "_" + id;
    }

    private HistoryLogItem buildLogItem(String id, String data, HistoryType type) {
        return new HistoryLogItem(id, new HistoryInfo(data, getContext().getUserId(), LocalDateTime.now(), type));
    }

    private boolean isHistoryService() {
        return this instanceof HistoryService;
    }
}
