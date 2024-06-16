package com.simpletiktok.simpletiktok.data.controller;

import com.simpletiktok.simpletiktok.data.entity.Love;
import com.simpletiktok.simpletiktok.data.entity.User;
import com.simpletiktok.simpletiktok.data.entity.Video;
import com.simpletiktok.simpletiktok.data.service.ILoveService;
import com.simpletiktok.simpletiktok.data.service.IUserService;
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
    @Autowired
    private IUserService UserService;

    @PostMapping("/sign")
    public ResponseResult<Map<String, String>> sign(@RequestBody Map params)
    {
        String author = (String) params.get("author");
        String password = (String) params.get("password");
        String confirmedPassword = (String) params.get("confirmedPassword");

        return ResponseResult.success(UserService.register(author, password, confirmedPassword));
    }

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
        if (!hasPermission(author, aweme_id)) {
            return ResponseResult.failure(403, "用户没有权限删除该视频");
        }

        boolean isRemoved = VideoService.removeById(aweme_id);
        if (isRemoved) {
            return ResponseResult.success(true);
        } else {
            return ResponseResult.failure(400, "删除视频失败");
        }
    }

    public boolean hasPermission(String author, String aweme_id) {
        Video video = VideoService.getById(aweme_id);
        if (video == null) {
            return false;
        }
        // 验证当前用户是否是视频的作者
        return video.getAuthor().equals(author);
    }

}
