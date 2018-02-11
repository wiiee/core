package com.wiiee.core.web.interceptor;

import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.log.CommonError;
import com.wiiee.core.platform.log.LogEntry;
import com.wiiee.core.platform.log.LogEntryPool;
import com.wiiee.core.platform.log.LoggerChain;
import com.wiiee.core.web.context.WebContext;
import com.wiiee.core.web.context.WebContextPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by wiiee on 3/5/2017.
 */
@Component
public class ContextInterceptor extends HandlerInterceptorAdapter {
    private IContextRepository contextRepository;
    private LoggerChain loggerChain;

    @Autowired
    private LogEntryPool logEntryPool;

    @Autowired
    private WebContextPool webContextPool;

    @Autowired
    public ContextInterceptor(IContextRepository contextRepository, LoggerChain loggerChain) {
        this.contextRepository = contextRepository;
        this.loggerChain = loggerChain;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", System.nanoTime());
        HttpSession session = request.getSession();

        contextRepository.setContext(
                webContextPool.allocate().build(
                        session == null ? null : (String) session.getAttribute("userId"),
                        session == null ? null : session.getId(),
                        request.getRequestedSessionId(),
                        request.getRequestURI(),
                        request.getRemoteAddr()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long elapsed_milliseconds = (System.nanoTime() - (Long) request.getAttribute("startTime")) / 1000000;

        String className = "";
        String methodName = "";

        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            className = method.getBeanType().getName();
            methodName = method.getMethod().getName();
        }

        LogEntry entry = logEntryPool.allocate()
                .build(
                        contextRepository.getCurrent(),
                        "Web",
                        className,
                        methodName,
                        CommonError.NoError.value(),
                        null,
                        contextRepository.getCurrent().getRequest(),
                        contextRepository.getCurrent().getResponse(),
                        elapsed_milliseconds,
                        null);

        loggerChain.log(entry);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        webContextPool.free(contextRepository.getContext(WebContext.class));
    }
}
