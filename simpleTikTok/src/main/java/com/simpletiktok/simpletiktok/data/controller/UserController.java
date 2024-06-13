package com.simpletiktok.simpletiktok.data.controller;

import com.simpletiktok.simpletiktok.data.entity.Love;
import com.simpletiktok.simpletiktok.data.service.ILoveService;
import com.simpletiktok.simpletiktok.data.service.IVideoService;
import com.simpletiktok.simpletiktok.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ILoveService LoveService;
    @Autowired
    private IVideoService VideoService;

    @PostMapping("/diggVideo")
    public ResponseResult<Boolean> diggVideo(@RequestBody Map params) {
        Love love = new Love();
        love.setAuthor((String) params.get("author"));
        love.setAwemeId((String) params.get("aweme_id"));
        love.setIsloved(String.valueOf(params.get("isLoved")));
        boolean isSaved = LoveService.save(love);
        if (isSaved) {
            return ResponseResult.success(true);
        } else {
            return ResponseResult.failure(400, "点赞失败");
        }
    }

    @DeleteMapping("/deleteVideo")
    public ResponseResult<Boolean> deleteVideo(@RequestParam String author, @RequestParam String aweme_id) {
        boolean isRemoved = VideoService.removeById(aweme_id);
        if (isRemoved) {
            return ResponseResult.success(true);
        } else {
            return ResponseResult.failure(400,"删除视频失败");
        }
    }
}
