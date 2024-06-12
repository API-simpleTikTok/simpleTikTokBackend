package com.simpletiktok.simpletiktok.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.simpletiktok.simpletiktok.data.entity.Video;
import com.simpletiktok.simpletiktok.data.service.IVideoService;
import com.simpletiktok.simpletiktok.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ricetea
 * @since 2024-06-06
 */
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private IVideoService videoService;

    @GetMapping("/my")
    public ResponseResult<Map<String, Object>> getMyVideo(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String author) {
        List<Video> videoList = videoService.getMyVideo(pageNo, pageSize, author);
        LambdaQueryWrapper<Video> countQueryWrapper = new LambdaQueryWrapper<>();
        countQueryWrapper.eq(Video::getAuthor, author);
        int totalVideos = (int) videoService.count(countQueryWrapper);
        Map<String, Object> videoPage = new HashMap<>();
        videoPage.put("pageNo", pageNo);
        videoPage.put("total", totalVideos);
        videoPage.put("list", videoList);
        return ResponseResult.success(videoPage);
    }
}
