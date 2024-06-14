package com.simpletiktok.simpletiktok.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.simpletiktok.simpletiktok.config.SecurityConstant;
import com.simpletiktok.simpletiktok.data.entity.User;
import com.simpletiktok.simpletiktok.data.mapper.UserMapper;
import com.simpletiktok.simpletiktok.data.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ricetea
 * @since 2024-06-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityConstant securityConstant;

    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword)
    {
        Map<String, String> map = new HashMap<>();

        if(username == null)
        {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        username = username.trim();
        username = username.trim();
        if (username.isEmpty())
        {
            map.put("error_message", "用户名不能为空");
            return map;
        }


        if(username.length() > 100)
        {
            map.put("error_message", "用户名过长");
            return map;
        }

        if (password == null || confirmedPassword == null) {
            map.put("error_message", "密码不能为空");
            return map;
        }

        if (password.isEmpty() || confirmedPassword.isEmpty()) {
            map.put("error_message", "密码不能为空");
        }

        if(password.length() > 100)
        {
            map.put("error_message", "密码过长");
            return map;
        }

        if(!password.equals(confirmedPassword))
        {
            map.put("error_message", "密码不一致");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if(!users.isEmpty())
        {
            map.put("error_message", "用户已存在");
            return map;
        }

        String encodedPassword = passwordEncoder.encode(password);

        //随机生成nickname，格式为“用户”+随机数
        String nickname = "用户" + (int)(Math.random() * 1000);

        User user = new User(username, nickname, encodedPassword,
                "http://sen0fbsqd.hb-bkt.clouddn.com/1945347711_avatar.jpg", 0, -1, 0, 0, 0,
                "", 0, "http://sen0fbsqd.hb-bkt.clouddn.com/aTnyHICCi-NMudWfVELeO.png");

        userMapper.insert(user);

        map.put("error_message", "success");
        securityConstant.update();
        return map;
    }
}
