package com.simpletiktok.simpletiktok.config;


import com.simpletiktok.simpletiktok.utils.JwtUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig
{
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expirationTime}")
    private long expirationTime;
    @Value("${jwt.header}")
    private String header;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @PostConstruct
    public void jwtInit()
    {
        JwtUtils.initialize(header, tokenHead, issuer, secretKey, expirationTime);
    }

}
