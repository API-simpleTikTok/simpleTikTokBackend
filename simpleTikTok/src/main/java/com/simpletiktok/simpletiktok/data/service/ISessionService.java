package com.simpletiktok.simpletiktok.data.service;

import java.util.Map;

/**
 * @author Silva31
 * @version 1.0
 * @date 2024/6/14 下午1:45
 */
public interface ISessionService
{
    Map<String, String> loginSession(String username, String password);

    String logoutSession(String author);
}
