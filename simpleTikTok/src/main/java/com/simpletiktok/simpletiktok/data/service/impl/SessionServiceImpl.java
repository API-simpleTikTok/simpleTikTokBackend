package com.simpletiktok.simpletiktok.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.simpletiktok.simpletiktok.data.entity.User;
import com.simpletiktok.simpletiktok.data.mapper.UserMapper;
import com.simpletiktok.simpletiktok.data.service.ISessionService;
import com.simpletiktok.simpletiktok.utils.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public Map<String, String> loginSession(String author, String password)
    {
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(author, password);
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author", author);
        User user = userMapper.selectOne(queryWrapper);
        Map<String, String> map = new HashMap<>();
        map.put("token", JwtUtils.generateToken(author, user.getVersion()));
        return map;
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
