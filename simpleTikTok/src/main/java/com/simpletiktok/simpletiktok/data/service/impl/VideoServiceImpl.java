package com.simpletiktok.simpletiktok.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.simpletiktok.simpletiktok.data.entity.Video;
import com.simpletiktok.simpletiktok.data.mapper.VideoMapper;
import com.simpletiktok.simpletiktok.data.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
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
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

    @Override
    public List<Video> getMyVideo(Integer pageNo, Integer pageSize, String author) {
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Video::getAuthor, author);
        queryWrapper.last("limit " + pageNo * pageSize + "," + pageSize);
        return list(queryWrapper);
    }

    @Override
    public List<Video> getRecommendedVideo(Integer start, Integer pageSize, String author) {
        Integer pageNo = 0;
        if(start != 0)
            pageNo = start / pageSize;
        else
            pageNo = 1;


        List<String> likedVideoIds = getLikedVideoIdsByAuthor(author);

        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();

        // 排除用户喜欢过的视频
        if (!likedVideoIds.isEmpty()) {
            queryWrapper.notIn(Video::getAwemeId, likedVideoIds);
        }

        queryWrapper.orderByDesc(Video::getDiggCount);
        queryWrapper.last("limit " + pageNo * pageSize + "," + pageSize);
        return list(queryWrapper);
    }

    @Override
    public List<String> getLikedVideoIdsByAuthor(String author)
    {
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Video::getAuthor, author);
        queryWrapper.select(Video::getAwemeId);
        return listObjs(queryWrapper, o -> (String) o);
    }
}
