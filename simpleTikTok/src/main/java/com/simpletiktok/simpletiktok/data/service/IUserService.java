package com.simpletiktok.simpletiktok.data.service;

import com.simpletiktok.simpletiktok.data.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ricetea
 * @since 2024-06-06
 */
public interface IUserService extends IService<User> {
    Map<String, String> register(String username, String password);
}
