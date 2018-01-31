package com.wiiee.core.domain.config;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by wang.na on 2016/12/7.
 */
//@Configuration
//@EnableCaching(proxyTargetClass = true)
public class CaffeineCacheConfig {
//    @Bean
//    public CacheManager cacheManager() {
//        String specAsString = "initialCapacity=100,maximumSize=500,expireAfterAccess=5m,recordStats";
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager("comment");
//        cacheManager.setAllowNullValues(false); //can happen if you get a value from a @Cachable that returns null
//        //cacheManager.setCacheSpecification(specAsString);
//        //cacheManager.setCaffeineSpec(caffeineSpec());
//        cacheManager.setCaffeine(caffeineCacheBuilder());
//        return cacheManager;
//    }
//
//    CaffeineSpec caffeineSpec() {
//        return CaffeineSpec.parse("initialCapacity=100,maximumSize=500,expireAfterAccess=5m,recordStats");
//    }
//
//    Caffeine<Object, Object> caffeineCacheBuilder() {
//        return Caffeine.newBuilder()
//                .initialCapacity(100)
//                .maximumSize(150)
//                .expireAfterAccess(5, TimeUnit.MINUTES)
//                .weakKeys()
//                .removalListener(new CustomRemovalListener())
//                .recordStats();
//    }
//
//    class CustomRemovalListener implements RemovalListener<Object, Object> {
//        @Override
//        public void onRemoval(Object key, Object value, RemovalCause cause) {
//            System.out.format("removal listerner called with key [%s], cause [%s], evicted [%S]\n",
//                    key, cause.toString(), cause.wasEvicted());
//        }
//    }
}
