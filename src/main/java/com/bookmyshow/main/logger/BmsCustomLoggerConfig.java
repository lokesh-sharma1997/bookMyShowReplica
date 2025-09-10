package com.bookmyshow.main.logger;
 
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
 
@Aspect
@Component
public class BmsCustomLoggerConfig {
	
    public static final Logger LOGGER = LogManager.getLogger(BmsCustomLoggerConfig.class);
        
    @Around("execution(* com.bookmyshow.main.serviceImpl.*.*(..)) || execution(* com.bookmyshow.main.config.*.*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        StringBuilder methodName = new StringBuilder();
        methodName.append(methodSignature.getDeclaringTypeName());
        methodName.append(".");
        methodName.append(methodSignature.getName());
        StringBuilder startMsg = new StringBuilder(methodName.toString());
        startMsg.append("(");
        startMsg.append(Arrays.stream(proceedingJoinPoint.getArgs()).map(String::valueOf).collect(Collectors.joining(",")));
        startMsg.append(") started");
        LOGGER.info(startMsg);
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retVal = proceedingJoinPoint.proceed();
        stopWatch.stop();
        StringBuilder endMsg = new StringBuilder(methodName.toString());
        endMsg.append(" executed in :");
        endMsg.append(stopWatch.getTotalTimeMillis());
        endMsg.append(" ms");
        LOGGER.info(endMsg);
        return retVal;
    }
}