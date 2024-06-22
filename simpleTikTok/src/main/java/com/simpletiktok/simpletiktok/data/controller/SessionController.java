package com.simpletiktok.simpletiktok.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.zxing.WriterException;
import com.simpletiktok.simpletiktok.annotation.Time;
import com.simpletiktok.simpletiktok.data.entity.User;
import com.simpletiktok.simpletiktok.data.mapper.UserMapper;
import com.simpletiktok.simpletiktok.data.service.ISessionService;
import com.simpletiktok.simpletiktok.utils.ValidationGroups;
import com.simpletiktok.simpletiktok.vo.ResponseResult;
import jakarta.annotation.Resource;
import com.simpletiktok.simpletiktok.utils.GoogleAuthenticationTool;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;

import java.io.IOException;
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
    @Autowired
    private UserMapper userMapper;
    private RedirectAttributes redirectAttributes;

    @Time
    @PostMapping("")
    public ResponseResult<Map<String, Object>> login(@RequestBody @Validated(ValidationGroups.UserValidation.class) User user) {
        String author = user.getAuthor();
        String password = user.getPassword();
        String token = sessionService.loginSession(author, password);
        Map<String, Object> map = new HashMap<>();
        if(token != null) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("author", author);
            User DBuser = userMapper.selectOne(queryWrapper);
            if(DBuser.getTwoFactorCode()==null|| DBuser.getTwoFactorCode().isEmpty())
            {
                map.put("code",203);
                map.put("redirect","/user/bindingGoogleTwoFactorValidate");
                return ResponseResult.success(map);
            }
            String rightGoogleCode = GoogleAuthenticationTool.getTOTPCode(DBuser.getTwoFactorCode());
            if(!user.getTwoFactorCode().equals(rightGoogleCode)){
                map.put("msg","谷歌验证码不正确或已超时");
                map.put("code",401);
                return ResponseResult.success(map);
            }
            map.put("token", token);
            return ResponseResult.success(map);
        }else{
            return ResponseResult.failure(401,"账户或密码不正确");
        }

    }

    @DeleteMapping("/logout")
    public ResponseResult<String> logout(@RequestParam @NotEmpty(message = "author 不能为空") String author) {
        return ResponseResult.success(sessionService.logoutSession(author));
    }
}
