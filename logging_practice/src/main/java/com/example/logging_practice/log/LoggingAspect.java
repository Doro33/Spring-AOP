package com.example.logging_practice.log;

import com.example.logging_practice.log.repository.ErrorLogRepository;
import com.example.logging_practice.log.repository.InfoLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    private final InfoLogRepository infoLogRepo;

    private final ErrorLogRepository errorLogRepo;

    @Autowired
    public LoggingAspect(InfoLogRepository infoLogRepo, ErrorLogRepository errorLogRepo) {
        this.infoLogRepo = infoLogRepo;
        this.errorLogRepo = errorLogRepo;
    }

    @AfterReturning(pointcut = "@annotation(com.example.logging_practice.log.Loggable)", returning = "result")
    public void logSuccess(JoinPoint joinPoint, Object result) {
        log.info("Method {} called with args {} → returned {}",
                joinPoint.getSignature().getName(), joinPoint.getArgs(), result);

        InfoLog infoLog = new InfoLog();
        infoLog.setMethodName(joinPoint.getSignature().toLongString());
        infoLog.setInput(Arrays.toString(joinPoint.getArgs()));
        infoLog.setResult(result != null ? result.toString() : "null");

        infoLogRepo.save(infoLog);
    }

    @AfterThrowing(pointcut = "@annotation(com.example.logging_practice.log.Loggable)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error("Method {} called with args {} → threw exception: {}",
                joinPoint.getSignature().getName(), joinPoint.getArgs(), Arrays.toString(ex.getStackTrace()));

        ErrorLog errorLog = new ErrorLog();
        errorLog.setMethodName(joinPoint.getSignature().toShortString());
        errorLog.setInput(Arrays.toString(joinPoint.getArgs()));
        errorLog.setExceptionMessage(ex.getMessage());
        errorLogRepo.save(errorLog);
    }
}
