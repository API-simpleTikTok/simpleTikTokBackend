package com.simpletiktok.simpletiktok.data.service.impl;

import com.simpletiktok.simpletiktok.data.service.ISessionService;
import com.simpletiktok.simpletiktok.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Silva31
 * @version 1.0
 * @date 2024/6/14 下午1:45
 */

@Service
public class SessionServiceImpl implements ISessionService
{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> loginSession(String author, String password)
    {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(author, password);

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        Map<String, String> map = new HashMap<>();
        map.put("token", JwtUtils.generateToken(author));
        return map;
    }
}
