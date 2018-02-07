package com.wiiee.core.web.interceptor;

import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.log.Category;
import com.wiiee.core.platform.log.CommonError;
import com.wiiee.core.platform.log.LogEntry;
import com.wiiee.core.platform.log.LoggerChain;
import com.wiiee.core.web.context.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by wiiee on 3/5/2017.
 */
@Component
public class ContextInterceptor extends HandlerInterceptorAdapter {
    private IContextRepository contextRepository;
    private LoggerChain loggerChain;

    private ThreadLocal<Long> startTime;

    @Autowired
    public ContextInterceptor(IContextRepository contextRepository, LoggerChain loggerChain) {
        this.contextRepository = contextRepository;
        this.loggerChain = loggerChain;
        startTime = new NamedThreadLocal<>("ContextInterceptor.startTime");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        startTime.set(System.nanoTime());
        contextRepository.setContext(
                new WebContext(
                        request.getRequestedSessionId(),
                        UUID.randomUUID().toString(),
                        request.getRequestURI(),
                        request.getRemoteAddr()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long duration = (System.nanoTime() - startTime.get()) / 1000000;

        String className = "";
        String methodName = "";

        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            className = method.getBeanType().getName();
            methodName = method.getMethod().getName();
        }

        LogEntry entry = new LogEntry(
                contextRepository.getCurrent(),
                Category.Web.name(),
                className,
                methodName,
                CommonError.NoError.value(),
                null,
                duration,
                null);

        loggerChain.log(entry);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
