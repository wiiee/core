package com.wiiee.core.web.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyExceptionAdvice {
    private static final Logger _logger = LoggerFactory.getLogger(MyExceptionAdvice.class);

    //ToDo: log exception to elasticsearch
    @ExceptionHandler(value = Exception.class)
    public void handleException(HttpServletRequest request, Exception ex){
        _logger.error(String.format("uri: %s; msg: %s"), request.getRequestURI(), ex.getMessage());
    }
}
