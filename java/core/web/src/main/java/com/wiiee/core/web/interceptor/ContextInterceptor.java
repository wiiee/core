package com.wiiee.core.web.interceptor;

import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.log.CommonError;
import com.wiiee.core.platform.log.LogEntry;
import com.wiiee.core.platform.log.LogEntryPool;
import com.wiiee.core.platform.log.LoggerChain;
import com.wiiee.core.platform.util.GsonUtil;
import com.wiiee.core.web.context.WebContext;
import com.wiiee.core.web.context.WebContextPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
    private static final Logger _logger = LoggerFactory.getLogger(ContextInterceptor.class);

    private IContextRepository contextRepository;
    private LoggerChain loggerChain;
    private LogEntryPool logEntryPool;
    private WebContextPool webContextPool;

    @Autowired
    public ContextInterceptor(IContextRepository contextRepository, LoggerChain loggerChain, LogEntryPool logEntryPool, WebContextPool webContextPool) {
        this.contextRepository = contextRepository;
        this.loggerChain = loggerChain;
        this.logEntryPool = logEntryPool;
        this.webContextPool = webContextPool;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", System.nanoTime());
        HttpSession session = request.getSession();

        String userId = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.getPrincipal() instanceof User){
            userId = ((User)authentication.getPrincipal()).getUsername();
        }

        contextRepository.setContext(
                webContextPool.allocate().build(
                        userId,
                        session == null ? null : session.getId(),
                        request.getRequestedSessionId(),
                        request.getRequestURI(),
                        request.getRemoteAddr()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long elapsed_milliseconds = (System.nanoTime() - (Long) request.getAttribute("startTime")) / 1000000;

        WebContext context = contextRepository.getContext(WebContext.class);

        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;

            String className = method.getBeanType().getName();
            String methodName = method.getMethod().getName();

            try {
                LogEntry entry = logEntryPool.allocate()
                        .build(
                                context,
                                "Web",
                                className,
                                methodName,
                                CommonError.NoError.value(),
                                null,
                                elapsed_milliseconds,
                                null);

                loggerChain.log(entry);
            } catch (Exception ex) {
                _logger.error(ex.getMessage());
            } finally {
                webContextPool.free(context);
            }
        }
        else{
            _logger.info("contextInterceptor: " + GsonUtil.toJson(context));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
