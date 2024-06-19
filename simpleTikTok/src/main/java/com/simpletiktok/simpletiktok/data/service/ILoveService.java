package com.simpletiktok.simpletiktok.data.service;

import com.simpletiktok.simpletiktok.data.entity.Love;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ricetea
 * @since 2024-06-06
 */
public interface ILoveService extends IService<Love>
{
    List<Love> getLoveByAuthor(String author, String aweme_id);
    boolean updateLoveStatus(String author, String newStatus, String aweme_id);
}
