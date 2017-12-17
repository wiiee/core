package com.dataaccess.cache;

import com.dataaccess.service.IService;
import com.platform.util.GsonUtil;
import org.ehcache.config.CacheRuntimeConfiguration;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expiry;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.support.NullValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by wang.na on 2017/7/20.
 */
@Component
public class CacheHelper implements ApplicationContextAware {
    @Autowired
    private JCacheCacheManager cacheManager;

    private Map<String, ClassPair> cacheMetas;

    public CacheHelper(){
        cacheMetas = new HashMap<>();
    }

    public Collection<String> getCacheNames(){
        return cacheManager == null ? null : cacheManager.getCacheNames();
    }

    public Collection<String> getCacheKeys(String cacheName){
        if(cacheManager == null){
            return null;
        }

        Collection<String> keys = new ArrayList<>();

        Cache cache = cacheManager.getCache(cacheName);

        if(cache != null){
            if(cache.getNativeCache() instanceof javax.cache.Cache){
                javax.cache.Cache nativeCache = (javax.cache.Cache) cache.getNativeCache();
                Iterator<javax.cache.Cache.Entry> it = nativeCache.iterator();

                Eh107Configuration configuration = (Eh107Configuration)nativeCache.getConfiguration(Eh107Configuration.class);
                CacheRuntimeConfiguration runtimeConfiguration = (CacheRuntimeConfiguration) configuration.unwrap(CacheRuntimeConfiguration.class);

                Expiry expiry = runtimeConfiguration.getExpiry();

                while (it.hasNext()) {
                    javax.cache.Cache.Entry entry = it.next();

                    Duration duration = expiry.getExpiryForCreation(entry.getKey(), entry.getValue());

                    if(entry != null){
                        keys.add(GsonUtil.toJson(entry.getKey()));
                    }
                }
            }
        }

        return keys;
    }

    public void clearCache(String cacheName){
        if(cacheManager == null){
            return;
        }

        Cache cache = cacheManager.getCache(cacheName);

        if(cache != null){
            cache.clear();
        }
    }

    public void evictCache(String cacheName, Object key){
        if(cacheManager == null){
            return;
        }

        Cache cache = cacheManager.getCache(cacheName);

        if(cache != null){
            cache.evict(key);
        }
    }

    public void updateCache(String cacheName, Object key, Object value){
        if(cacheManager == null){
            return;
        }

        Cache cache = cacheManager.getCache(cacheName);

        if(cache != null){
            cache.putIfAbsent(key, value);
        }
    }

    public void updateCacheWithString(String cacheName, String key, String value){
        Object oKey = GsonUtil.fromJson(key, cacheMetas.get(cacheName).getKey());
        Object oValue = GsonUtil.fromJson(value, cacheMetas.get(cacheName).getValue());

        if(oKey != null && oValue != null){
            updateCache(cacheName, oKey, oValue);
        }
    }

    public Object getCacheValue(String cacheName, Object key){
        if(cacheManager != null && key != null){
            Cache cache = cacheManager.getCache(cacheName);

            if(cache != null){
                return cache.get(key);
            }
        }

        return null;
    }

    public String getCacheValueWithString(String cacheName, String key){
        Object oKey = GsonUtil.fromJson(key, cacheMetas.get(cacheName).getKey());

        if(oKey == null){
            return "empty";
        }

        Object value = getCacheValue(cacheName, oKey);

        if(value == null){
            return "empty";
        }

        return value instanceof NullValue ? "null" : GsonUtil.toJson(value);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String ,IService> services = applicationContext.getBeansOfType(IService.class);

        for(IService service : services.values()){
            Class clazz = service.getClass();

            if(clazz.getSimpleName().contains("EnhancerBySpringCGLIB")){
                clazz = service.getClass().getSuperclass();
            }

            for(Method method : clazz.getDeclaredMethods()){
                if(method.isAnnotationPresent(Cacheable.class)){
                    Cacheable annotation = method.getAnnotation(Cacheable.class);

                    cacheMetas.put(annotation.cacheNames()[0],
                            new ClassPair(method.getParameterTypes()[0], method.getReturnType()));
                }
            }
        }
    }

    public static class ClassPair{
        private Class key;
        private Class value;

        public ClassPair(Class key, Class value) {
            this.key = key;
            this.value = value;
        }

        public Class getKey() {
            return key;
        }

        public Class getValue() {
            return value;
        }
    }
}
