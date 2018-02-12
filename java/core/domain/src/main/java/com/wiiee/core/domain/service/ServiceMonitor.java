package com.wiiee.core.domain.service;

import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.log.CommonError;
import com.wiiee.core.platform.log.LogEntry;
import com.wiiee.core.platform.log.LogEntryPool;
import com.wiiee.core.platform.log.LoggerChain;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMonitor {
    private LoggerChain loggerChain;
    private LogEntryPool logEntryPool;
    private IContextRepository contextRepository;

    @Autowired
    public ServiceMonitor(LoggerChain loggerChain, LogEntryPool logEntryPool, IContextRepository contextRepository) {
        this.loggerChain = loggerChain;
        this.logEntryPool = logEntryPool;
        this.contextRepository = contextRepository;
    }

    @Around("execution(public com.wiiee.core.domain.service.ServiceResult com..service.*Service.*(..))")
    public ServiceResult logService(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.nanoTime();
        ServiceResult retVal = (ServiceResult) pjp.proceed();
        long endTime = System.nanoTime();

        long elapsed_milliseconds = (endTime - startTime) / 1000000;

        LogEntry entry = logEntryPool.allocate().build(
                contextRepository.getCurrent(),
                "Service",
                pjp.getTarget().getClass().getName(),
                pjp.getSignature().getName(),
                retVal.isSuccessful ? CommonError.NoError.value() : CommonError.ServiceError.value(),
                retVal.isSuccessful ? null : retVal.message,
                elapsed_milliseconds,
                retVal.data == null ? retVal.datum : null);
        loggerChain.log(entry);

        return retVal;
    }
}
