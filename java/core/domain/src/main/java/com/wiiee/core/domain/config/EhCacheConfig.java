package com.wiiee.core.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * Created by wang.na on 2016/12/7.
 */
@Configuration
@EnableCaching(proxyTargetClass = true)
public class EhCacheConfig {
//    @Bean
//    public CacheManager cacheManager() {
//        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
//    }
//
//    @Value("classpath:ehcache3.xml")
//    public Resource configLocation;
//
//    @Bean
//    public EhCacheManagerFactoryBean ehCacheCacheManager() {
//        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
//        cmfb.setConfigLocation(configLocation);
//        cmfb.setShared(true);
//        return cmfb;
//    }
}
