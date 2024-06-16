package com.simpletiktok.simpletiktok.data.controller;

import com.simpletiktok.simpletiktok.data.service.ISessionService;
import com.simpletiktok.simpletiktok.vo.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Silva31
 * @version 1.0
 * @date 2024/6/14 下午1:52
 */

@RestController
@RequestMapping("/session")
public class SessionController
{
    @Resource
    ISessionService sessionService;

    @PostMapping("")
    public ResponseResult<Map<String, String>> login(@RequestBody HashMap hashMap)
    {
        String author = (String) hashMap.get("author");
        String password = (String) hashMap.get("password");

        return ResponseResult.success(sessionService.loginSession(author, password));
    }

    @DeleteMapping("")
    public ResponseResult<String> logout( @RequestParam String author)
    {
        return ResponseResult.success(sessionService.logoutSession(author));
    }
}
