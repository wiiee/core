package com.wiiee.core.domain.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * Created by wang.na on 2017/1/4.
 */
//@Configuration
//@EnableCaching(proxyTargetClass = true)
public class RedisCacheConfig {
//    @Bean
//    public CacheManager cacheManager(StringRedisTemplate redisTemplate) {
//        RedisCacheManager cacheManager =  new RedisCacheManager(redisTemplate);
//        cacheManager.setUsePrefix(true);
//        cacheManager.setDefaultExpiration(1000);
//        return cacheManager;
//    }
//
//    @Bean
//    public StringRedisTemplate redisTemplate(
//            RedisConnectionFactory factory) {
//        final StringRedisTemplate template = new StringRedisTemplate(factory);
//        setSerializer(template);
//        template.afterPropertiesSet();
//
//        return template;
//    }
//
//    @Bean
//    public KeyGenerator redisKeyGenerator(){
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//                //sb.append(target.getClass().getName());
//                //sb.append(method.getName());
//                for (Object obj : params) {
//                    sb.append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }
//
//    private void setSerializer(StringRedisTemplate template) {
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//    }
}
