package com.simpletiktok.simpletiktok.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@Component
public class LoggingAspect
{
    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ||" +
            "@annotation(com.simpletiktok.simpletiktok.annotation.LogExecutionTime)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable
    {
        // 生成一个唯一的 logID
        String logId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        // 设置 logID 到 MDC
        MDC.put("logId", logId);

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        requestAttributes.setAttribute("logId", logId, RequestAttributes.SCOPE_REQUEST);

        //系统信息
        String systemInfo = getSystemInfo();
        LoggerFactory.getLogger(joinPoint.getTarget().getClass()).info("[" + logId + "] " + "System Info: {}", systemInfo);

        // 记录入参
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String args = objectMapper.writeValueAsString(joinPoint.getArgs());
        LoggerFactory.getLogger(joinPoint.getTarget().getClass()).info("[{}] Request: {}", logId, args);

        // 调用原方法
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = method.getName();
        LoggerFactory.getLogger(joinPoint.getTarget().getClass()).info("[{}] {}.{} executed in {} ms", logId, className, methodName, executionTime);

        // 记录出参
        String response = objectMapper.writeValueAsString(result);
        LoggerFactory.getLogger(joinPoint.getTarget().getClass()).info("[{}] Response: {}", logId, response);

        // 清除 MDC 中的 logID
        MDC.remove("logId");

        return result;
    }

    public static String getSystemInfo() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        return "OS: " + System.getProperty("os.name") + ", Version: " + System.getProperty("os.version")
                + ", JVM Name: " + runtimeBean.getVmName() + ", JVM Version: " + runtimeBean.getVmVersion();
    }
}
