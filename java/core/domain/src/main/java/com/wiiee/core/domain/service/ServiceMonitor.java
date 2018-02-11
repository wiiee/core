package com.wiiee.core.domain.service;

import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.log.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMonitor {
    @Autowired
    private LoggerChain loggerChain;

    @Autowired
    private LogEntryPool logEntryPool;

    @Autowired
    private IContextRepository contextRepository;

    @Around("execution(public ServiceResult com.wiiee.core.domain.service.BaseService.*(..))")
    public ServiceResult logService(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.nanoTime();
        ServiceResult retVal = (ServiceResult) pjp.proceed();
        long endTime = System.nanoTime();

        long elapsed_milliseconds = (endTime - startTime) / 1000000;

        LogEntry entry = logEntryPool.allocate();

        if(retVal.isSuccessful){
            entry.build(
                    contextRepository.getCurrent(),
                    "BaseService",
                    pjp.getTarget().getClass().getName(),
                    pjp.getSignature().getName(),
                    CommonError.NoError.value(),
                    null,
                    pjp.getArgs(),
                    retVal.data == null ? retVal.datum : retVal.data,
                    elapsed_milliseconds,
                    null);
        }
        else{
            entry.build(
                    contextRepository.getCurrent(),
                    "BaseService",
                    pjp.getTarget().getClass().getName(),
                    pjp.getSignature().getName(),
                    CommonError.ServiceError.value(),
                    retVal.message,
                    pjp.getArgs(),
                    retVal.data == null ? retVal.datum : retVal.data,
                    elapsed_milliseconds,
                    null);
        }

        loggerChain.log(entry);

        return retVal;
    }
}
