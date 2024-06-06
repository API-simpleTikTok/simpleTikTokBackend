package com.simpletiktok.simpletiktok.interceptor;

import com.simpletiktok.simpletiktok.annotation.RateLimit;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Date;

@Component
public class RateLimitInterceptor implements HandlerInterceptor
{
    @Autowired
    private StringRedisTemplate redisTemplate;

//    private final long tokenBucketSize = 10; // 令牌桶容量
//    private final long refillRate = 100; // 每0.1秒放入一个令牌
//    private final long refillInterval = 100; // 令牌放入间隔

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
        if (!(handler instanceof HandlerMethod))
        {
            // 如果不是HandlerMethod实例，直接放行
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        if (rateLimit == null) return true;

        //对这一个Controller限制
        String key = "rate_limit:" + method.getDeclaringClass().getName();

        Date date = new Date();
        System.out.println(date);

        long currentTokens = getCurrentTokens(key);
        if (currentTokens <= 0) {
            return returnRateLimitExceeded(response);
        }

        currentTokens--;
        redisTemplate.opsForValue().set(key, String.valueOf(currentTokens));

        return true;
    }

    private boolean returnRateLimitExceeded(HttpServletResponse response)
    {
        response.setStatus(429);
        return false;
    }

    private synchronized long getCurrentTokens(String key)
    {
        String currentTokensStr = redisTemplate.opsForValue().get(key);
        return (currentTokensStr != null && !currentTokensStr.isEmpty()) ? Long.parseLong(currentTokensStr) : 0;
    }

    @Scheduled(fixedRate = 1000) // 每1秒执行一次
    public void refillTokens()
    {
        // 获取所有需要管理令牌桶的key
        // 需要修改
        String key = "com.dreamerland.design.controller.StudentsController";

//        String lastRefillTimeStr = redisTemplate.opsForValue().get("rate_limit:refill_time:" + key);
//        long lastRefillTime = (lastRefillTimeStr != null && !lastRefillTimeStr.isEmpty()) ? Long.parseLong(lastRefillTimeStr) : 0;
//        long currentTime = System.currentTimeMillis();
//
//        long timePassed = currentTime - lastRefillTime;
//        long tokensToAdd = timePassed / refillInterval * refillRate;
//        String currentTokensStr = redisTemplate.opsForValue().get("rate_limit:" + key);
//        long currentTokens = (currentTokensStr != null && !currentTokensStr.isEmpty()) ? Long.parseLong(currentTokensStr) : 0;
//        currentTokens = Math.min(currentTokens + tokensToAdd, tokenBucketSize); // 不能超过容量
        redisTemplate.opsForValue().set("rate_limit:" + key, String.valueOf(10));
//        redisTemplate.opsForValue().set("rate_limit:refill_time:" + key, String.valueOf(currentTime));
    }
}
