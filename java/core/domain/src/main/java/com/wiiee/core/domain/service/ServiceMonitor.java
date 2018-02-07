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
    private IContextRepository contextRepository;

    @Around("execution(public ServiceResult com.wiiee.core.domain.service.BaseService.*(..))")
    public ServiceResult logService(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.nanoTime();
        ServiceResult retVal = (ServiceResult) pjp.proceed();
        long endTime = System.nanoTime();

        ILogEntry entry = null;
        long duration = (endTime - startTime) / 1000000;

        if(retVal.isSuccessful){
            entry = new LogEntry(
                    contextRepository.getCurrent(),
                    Category.Service.name(),
                    pjp.getTarget().getClass().getName(),
                    pjp.getSignature().getName(),
                    CommonError.NoError.value(),
                    null,
                    duration,
                    retVal.data == null ? retVal.datum : retVal.data);
        }
        else{
            entry = new LogEntry(
                    contextRepository.getCurrent(),
                    Category.Service.name(),
                    pjp.getTarget().getClass().getName(),
                    pjp.getSignature().getName(),
                    CommonError.ServiceError.value(),
                    retVal.message,
                    duration,
                    retVal.data == null ? retVal.datum : retVal.data);
        }

        loggerChain.log(entry);

        return retVal;
    }
}
