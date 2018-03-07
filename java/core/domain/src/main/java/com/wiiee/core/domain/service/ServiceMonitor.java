package com.wiiee.core.domain.service;

import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.exception.CoreException;
import com.wiiee.core.platform.log.BaseLogEntry;
import com.wiiee.core.platform.log.LoggerFacade;
import com.wiiee.core.domain.log.ServiceLogEntryPool;
import com.wiiee.core.platform.log.other.OtherLogEntry;
import com.wiiee.core.platform.log.other.OtherLogEntryPool;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMonitor {
    private LoggerFacade loggerFacade;
    private IContextRepository contextRepository;
    private ServiceLogEntryPool logEntryPool;
    private OtherLogEntryPool otherLogEntryPool;

    @Autowired
    public ServiceMonitor(LoggerFacade loggerFacade, ServiceLogEntryPool logEntryPool,
                          OtherLogEntryPool otherLogEntryPool,
                          IContextRepository contextRepository) {
        this.loggerFacade = loggerFacade;
        this.logEntryPool = logEntryPool;
        this.otherLogEntryPool = otherLogEntryPool;
        this.contextRepository = contextRepository;
    }

    @Around("execution(public com.wiiee.core.domain.service.ServiceResult com..service.*Service.*(..))")
    public ServiceResult logService(ProceedingJoinPoint pjp) throws Throwable {
        try {
            long startTime = System.nanoTime();
            ServiceResult retVal = (ServiceResult) pjp.proceed();
            long endTime = System.nanoTime();

            long elapsed_milliseconds = (endTime - startTime) / 1000000;

            BaseLogEntry entry = logEntryPool.allocate().build(
                    contextRepository.getCurrent(),
                    pjp.getTarget().getClass().getName(),
                    pjp.getSignature().getName(),
                    retVal.errorCode,
                    retVal.errorMsg,
                    elapsed_milliseconds,
                    pjp.getArgs(),
                    retVal);

            loggerFacade.log(entry, logEntryPool);

            return retVal;
        } catch (Exception ex) {
            OtherLogEntry entry = otherLogEntryPool.allocate().build(ex, null);
            loggerFacade.log(entry, otherLogEntryPool);
            return ServiceResult.getByException(CoreException.EXCEPTION_SERVICE);
        }
    }
}
