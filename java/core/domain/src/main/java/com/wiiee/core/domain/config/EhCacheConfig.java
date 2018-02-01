package com.wiiee.core.domain.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

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
//    @Value("classpath:ehcache3-history.xml")
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
