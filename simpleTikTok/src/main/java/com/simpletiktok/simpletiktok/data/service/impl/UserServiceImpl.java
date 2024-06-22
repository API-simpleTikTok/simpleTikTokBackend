package com.simpletiktok.simpletiktok.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.simpletiktok.simpletiktok.config.SecurityConstant;
import com.simpletiktok.simpletiktok.data.entity.User;
import com.simpletiktok.simpletiktok.data.mapper.UserMapper;
import com.simpletiktok.simpletiktok.data.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpletiktok.simpletiktok.vo.ResponseResult;
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
    public ResponseResult<Object> register(String username, String password)
    {

        if(username == null)
        {
            return ResponseResult.failure(201, "用户名不能为空");
        }
        username = username.trim();
        username = username.trim();
        if (username.isEmpty())
        {
            return ResponseResult.failure(201, "用户名不能为空");
        }


        if(username.length() > 100)
        {
            return ResponseResult.failure(201, "用户名过长");
        }

        if (password == null ) {
            return ResponseResult.failure(201, "密码不能为空");
        }

        if (password.isEmpty() ) {
            return ResponseResult.failure(201, "用户名不能为空");
        }

        if(password.length() > 100)
        {
            return ResponseResult.failure(201, "密码过长");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if(!users.isEmpty())
        {
            return ResponseResult.failure(201, "用户已存在");
        }

        String encodedPassword = passwordEncoder.encode(password);

        //随机生成nickname，格式为“用户”+随机数
        String nickname = "用户" + (int)(Math.random() * 1000);

        User user = new User();
        user.setAuthor(username);
        user.setNickname(nickname);
        user.setPassword(encodedPassword);
        user.setAvatar("http://sen0fbsqd.hb-bkt.clouddn.com/1945347711_avatar.jpg");
        user.setFollower(0);
        user.setGender(1);
        user.setAwemeCount(0);
        user.setFollowerCount(0);
        user.setUserAge(19);
        user.setCoverUrl("http://sen0fbsqd.hb-bkt.clouddn.com/1945347711_avatar.jpg");
        user.setVersion(0);

        userMapper.insert(user);
        securityConstant.update();
        return ResponseResult.success("注册成功");
    }
}
