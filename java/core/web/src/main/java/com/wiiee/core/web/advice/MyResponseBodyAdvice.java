package com.wiiee.core.web.advice;

import com.wiiee.core.platform.context.IContext;
import com.wiiee.core.platform.context.IContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {
    @Autowired
    private IContextRepository contextRepository;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        IContext context = contextRepository.getCurrent();

        if(context != null){
            context.setResponse(body);
        }

        return body;
    }
}
