package com.simpletiktok.simpletiktok;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.simpletiktok.simpletiktok.data.mapper")
public class SimpleTikTokApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SimpleTikTokApplication.class, args);
    }

}
