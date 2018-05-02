package com.wiiee.core.domain.service;

import com.wiiee.core.platform.context.IContextRepository;
import com.wiiee.core.platform.exception.CoreException;
import com.wiiee.core.platform.log.LogItem;
import com.wiiee.core.platform.log.LoggerFacade;
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

    @Autowired
    public ServiceMonitor(IContextRepository contextRepository, LoggerFacade loggerFacade) {
        this.loggerFacade = loggerFacade;
        this.contextRepository = contextRepository;
    }

    @Around("execution(public com.wiiee.core.domain.service.ServiceResult com..service.*Service.*(..))")
    public ServiceResult logService(ProceedingJoinPoint pjp) throws Throwable {
        try {
            long startTime = System.nanoTime();
            ServiceResult retVal = (ServiceResult) pjp.proceed();
            long endTime = System.nanoTime();

            long elapsed_milliseconds = (endTime - startTime) / 1000000;

            LogItem item = new LogItem().build(
                    contextRepository.getCurrent(),
                    pjp.getTarget().getClass().getName(),
                    pjp.getSignature().getName(),
                    retVal.errorCode,
                    retVal.errorMsg,
                    elapsed_milliseconds,
                    pjp.getArgs(),
                    retVal,
                    null);

            loggerFacade.log(item);

            return retVal;
        } catch (Exception ex) {
            return ServiceResult.getByException(CoreException.EXCEPTION_SERVICE);
        }
    }
}
