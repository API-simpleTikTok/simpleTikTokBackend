package com.simpletiktok.simpletiktok.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@annotation(com.simpletiktok.simpletiktok.annotation.Time)")
    public void annotationPointcut() {
    }

    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        System.out.println("准备执行方法: " + joinPoint.getSignature().getName());
    }

    @After("annotationPointcut()")
    public void afterPointcut(JoinPoint joinPoint) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime.get();
        startTime.remove();  // 清理ThreadLocal变量，避免内存泄漏
        System.out.println("方法 " + joinPoint.getSignature().getName() + " 执行结束，耗时: " + duration + " ms");
    }
}
