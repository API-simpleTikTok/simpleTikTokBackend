package com.simpletiktok.simpletiktok.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {

    private static final Logger logger = LoggerFactory.getLogger(TimeAspect.class);
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@annotation(com.simpletiktok.simpletiktok.annotation.Time)")
    public void annotationPointcut() {
    }

    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        logger.info("准备执行方法: {}", joinPoint.getSignature().getName());
    }

    @After("annotationPointcut()")
    public void afterPointcut(JoinPoint joinPoint) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime.get();
        startTime.remove();  // 清理ThreadLocal变量，避免内存泄漏

        logger.info("方法 {} 执行结束，耗时: {} ms", joinPoint.getSignature().getName(), duration);
    }
}
