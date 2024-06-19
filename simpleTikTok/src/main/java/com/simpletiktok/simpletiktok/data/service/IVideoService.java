package com.simpletiktok.simpletiktok.data.service;

import com.simpletiktok.simpletiktok.data.entity.Video;
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
public interface IVideoService extends IService<Video> {

    List<Video> getMyVideo(Integer pageNo, Integer pageSize, String author);

    List<Video> getRecommendedVideo(Integer start, Integer pageSize, String author);

    List<String> getLikedVideoIdsByAuthor(String author);

    List<Video> getMyLikedVideos(Integer pageNo, Integer pageSize,String author);
}
