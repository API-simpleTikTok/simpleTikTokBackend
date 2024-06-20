package com.simpletiktok.simpletiktok.data.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Service
public class BloomFilterService {

    private BloomFilter<String> bloomFilter;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        // 初始化布隆过滤器，假设预估插入量为100000，误判率为0.01
        bloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 100000, 0.01);

        // 从Redis中加载已有数据到布隆过滤器
        loadBloomFilterFromRedis();
    }

    public void add(String item) {
        bloomFilter.put(item);
        redisTemplate.opsForSet().add("bloomFilterItems", item);
    }

    public boolean mightContain(String item) {
        return bloomFilter.mightContain(item);
    }

    private void loadBloomFilterFromRedis() {
        var items = redisTemplate.opsForSet().members("bloomFilterItems");
        if (items != null) {
            items.forEach(bloomFilter::put);
        }
    }
}

