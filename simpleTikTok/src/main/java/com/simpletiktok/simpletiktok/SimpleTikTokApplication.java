package com.simpletiktok.simpletiktok;

import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.simpletiktok.simpletiktok.data.mapper")
public class SimpleTikTokApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SimpleTikTokApplication.class, args);
    }

}
