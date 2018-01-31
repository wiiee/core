package com.wiiee.core.web.config;

import com.wiiee.core.web.interceptor.ContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wang.na on 2016/11/8.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private ContextInterceptor contextInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(contextInterceptor);
    }
}