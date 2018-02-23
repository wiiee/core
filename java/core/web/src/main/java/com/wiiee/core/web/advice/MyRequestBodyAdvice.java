package com.wiiee.core.web.advice;

import com.wiiee.core.platform.context.IContext;
import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.data.BaseData;
import com.wiiee.core.domain.security.IAccessCtrl;
import com.wiiee.core.web.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

@RestControllerAdvice
public class MyRequestBodyAdvice implements RequestBodyAdvice {
    @Autowired
    private IContextRepository contextRepository;

    @Autowired(required = false)
    private IAccessCtrl accessCtrl;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        IContext context = contextRepository.getCurrent();

        if (context != null) {
            context.setRequest(body);
        }

        if (body instanceof BaseData
                //匿名的跳过
                && !SecurityUtil.isAnonymous()) {
            //添加新用户跳过
            if (context != null
                    && context.getUri().equals("/api/user")
                    && context.getHttpMethod() == HttpMethod.PUT) {
                return body;
            }

            Authentication authentication = SecurityUtil.getAuthentication();

            if (accessCtrl != null && authentication != null) {
                String opUserId = ((BaseData) body).getId().toString();
                String authUserId = SecurityUtil.getUserId();

                if (!accessCtrl.isAllowed(authUserId, opUserId)) {
                    throw new org.springframework.security.access.AccessDeniedException(
                            String.format("opUserId: %s, authUserId: %s", opUserId, authUserId));
                }
            }
        }

        return body;
    }
}
