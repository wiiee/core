package com.wiiee.core.web.interceptor;

import com.wiiee.core.domain.service.ServiceResult;
import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.log.LogItem;
import com.wiiee.core.platform.log.LogWriter;
import com.wiiee.core.platform.util.GsonUtil;
import com.wiiee.core.web.context.WebContext;
import com.wiiee.core.web.context.WebContextPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wiiee on 3/5/2017.
 */
@Component
public class ContextInterceptor extends HandlerInterceptorAdapter {
    private static final Logger _logger = LoggerFactory.getLogger(ContextInterceptor.class);

    private IContextRepository contextRepository;
    private LogWriter logWriter;
    private WebContextPool webContextPool;

    @Autowired
    public ContextInterceptor(IContextRepository contextRepository, LogWriter logWriter, WebContextPool webContextPool) {
        this.contextRepository = contextRepository;
        this.logWriter = logWriter;
        this.webContextPool = webContextPool;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", System.nanoTime());

        contextRepository.setContext(webContextPool.allocate().build(request));
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
                ServiceResult result = null;
                if(context.getResponse() != null && context.getResponse() instanceof ServiceResult){
                    result = (ServiceResult)context.getResponse();
                }

                LogItem item = new LogItem().build(
                                context,
                                className,
                                methodName,
                                result == null ? 0 : result.errorCode,
                                result == null ? null : result.errorMsg,
                                elapsed_milliseconds,
                                context.getRequest(),
                                context.getResponse(), null);

                logWriter.log(item);
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
}
