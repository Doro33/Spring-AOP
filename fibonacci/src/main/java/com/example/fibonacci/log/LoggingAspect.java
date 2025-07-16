package com.example.fibonacci.log;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @AfterReturning(pointcut = "@annotation(com.example.fibonacci.log.Loggable)", returning = "result")
    public void logSuccess(JoinPoint joinPoint, Object result) {
        log.info("Method {} called with args {} → returned {}",
                joinPoint.getSignature().getName(), joinPoint.getArgs(), result);
    }

    @AfterThrowing(pointcut = "@annotation(com.example.fibonacci.log.Loggable)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error("Method {} called with args {} → threw exception: {}",
                joinPoint.getSignature().getName(), joinPoint.getArgs(), ex.getMessage());
    }
}