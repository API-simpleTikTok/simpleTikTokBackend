package com.simpletiktok.simpletiktok.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.simpletiktok.simpletiktok.data.entity.User;
import com.simpletiktok.simpletiktok.data.mapper.UserMapper;
import com.simpletiktok.simpletiktok.data.service.ISessionService;
import com.simpletiktok.simpletiktok.utils.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.mysql.cj.conf.PropertyKey.logger;

/**
 * @author Silva31
 * @version 1.0
 * @date 2024/6/14 下午1:45
 */

@Service
public class SessionServiceImpl implements ISessionService
{

    @Resource
    UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String loginSession(String author, String password)
    {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(author, password);
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            // 处理认证成功的情况
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (AuthenticationException e) {
            // 处理认证失败的情况
            System.out.println("Authentication failed: " + e.getMessage());
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author", author);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            return null;
        }
        if(!user.getPassword().equals(password)){
            return null;
        }
        String token = JwtUtils.generateToken(author, user.getVersion());
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("author", author).set("token",token);
        userMapper.update(null, updateWrapper);
        return token;
    }

    @Override
    public String logoutSession(String author)
    {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("author", author));
        user.setVersion(user.getVersion() + 1);
        userMapper.updateById(user);
        return "登出成功！";
    }
}
