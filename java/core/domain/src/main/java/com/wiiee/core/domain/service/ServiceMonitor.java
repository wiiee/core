package com.wiiee.core.domain.service;

import com.wiiee.core.platform.log.EsLogEntry;
import com.wiiee.core.platform.log.ILogEntry;
import com.wiiee.core.platform.log.LoggerChain;
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

    @Around("execution(public com.wiiee.core.domain.service.ServiceResult com.wiiee.core.domain.service.BaseService.*(..))")
    public ServiceResult logService(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.nanoTime();
        ServiceResult retVal = (ServiceResult) pjp.proceed();
        long endTime = System.nanoTime();

        ILogEntry entry = null;
        long duration = (endTime - startTime) / 1000000;

        if(retVal.isSuccessful){
            entry = new EsLogEntry("Service", pjp.getTarget().getClass().getName(), -1, null, retVal.data == null ? retVal.datum : retVal.data, duration);
        }
        else{
            entry = new EsLogEntry("Service", pjp.getTarget().getClass().getName(), 1, retVal.message, retVal.data == null ? retVal.datum : retVal.data, duration);
        }

        loggerChain.log(entry);

        return retVal;
    }
}
