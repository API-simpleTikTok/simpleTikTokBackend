package com.simpletiktok.simpletiktok.data.controller;

import com.simpletiktok.simpletiktok.data.entity.User;
import com.simpletiktok.simpletiktok.data.service.ISessionService;
import com.simpletiktok.simpletiktok.utils.ValidationGroups;
import com.simpletiktok.simpletiktok.vo.ResponseResult;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public ResponseResult<Map<String, String>> login(@RequestBody @Validated(ValidationGroups.UserValidation.class) User user) {

        String author = user.getAuthor();
        String password = user.getPassword();

        return ResponseResult.success(sessionService.loginSession(author, password));
    }

    @DeleteMapping("/logout")
    public ResponseResult<String> logout(@RequestParam @NotEmpty(message = "author 不能为空") String author) {
        return ResponseResult.success(sessionService.logoutSession(author));
    }
}
