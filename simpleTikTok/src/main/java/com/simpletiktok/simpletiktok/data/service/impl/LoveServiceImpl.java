package com.simpletiktok.simpletiktok.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.simpletiktok.simpletiktok.data.entity.Love;
import com.simpletiktok.simpletiktok.data.mapper.LoveMapper;
import com.simpletiktok.simpletiktok.data.service.ILoveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ricetea
 * @since 2024-06-06
 */
@Service
public class LoveServiceImpl extends ServiceImpl<LoveMapper, Love> implements ILoveService {
    @Autowired
    LoveMapper loveMapper;
    @Override
    public List<Love> getLoveByAuthor(String author, String aweme_id){
        return loveMapper.getMyLikedVideosByAuthor(author, aweme_id);
    }

    @Override
    public boolean updateLoveStatus(String author, String newStatus, String aweme_id) {
        LambdaUpdateWrapper<Love> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Love::getAuthor, author).set(Love::getAwemeId, aweme_id) // 设置更新条件
                .set(Love::getIsloved, newStatus); // 设置更新的字段和值

        return update(updateWrapper); // 执行更新操作
    }
}
