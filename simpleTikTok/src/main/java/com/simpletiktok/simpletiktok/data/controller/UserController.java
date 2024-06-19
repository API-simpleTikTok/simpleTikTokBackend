package com.simpletiktok.simpletiktok.data.controller;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.simpletiktok.simpletiktok.data.entity.Love;
import com.simpletiktok.simpletiktok.data.entity.User;
import com.simpletiktok.simpletiktok.data.entity.Video;
import com.simpletiktok.simpletiktok.data.mapper.UserMapper;
import com.simpletiktok.simpletiktok.data.service.ILoveService;
import com.simpletiktok.simpletiktok.data.service.IUserService;
import com.simpletiktok.simpletiktok.data.service.IVideoService;
import com.simpletiktok.simpletiktok.vo.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


import java.util.Collections;
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
@RequestMapping("/user")
public class UserController {
    @Resource
    private ILoveService LoveService;
    @Resource
    private IVideoService VideoService;
    @Resource
    private IUserService UserService;
    @Autowired
    private UserMapper userMapper;

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

    @GetMapping("/getUploadToken")
    public Object getUploadToken(@RequestParam String author) {
        User user = userMapper.selectById(author);
        if(user == null) {
            return new RedirectView("/login"); // 这里假设登录入口的URL为 /login
        }
        String accessKey = "RvSSLZzyvWnQoHd6qWRNJpV4E3ti2Ifemsxj6Xvc";
        String secretKey = "GH2eDLZEJAItCNQqORoqjcfH3jzFqZo8Z_TqPKQm";
        Auth auth = Auth.create(accessKey, secretKey);
        String bucket = "simpletiktok";
        long expireSeconds = 3600;
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        String upToken = auth.uploadToken(bucket,null,expireSeconds,putPolicy);
        Map<String, Object> res = new HashMap<>();
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("upToken",upToken);
        res.put("data",tokenMap);
        res.put("msg","获取成功");
        return ResponseResult.success(res);

    }

    @GetMapping("/panel")
    public ResponseResult<Map<String, Object>> panel(@RequestParam String author) {
        User user = userMapper.selectById(author);
        if(user == null) {
            return ResponseResult.failure(403,"未找到用户数据");
        }
        String avatar = user.getAvatar();
        Map<String, Object> author_list = new HashMap<>();
        author_list.put("aweme_count",userMapper.selectById(author).getAwemeCount());
        author_list.put("follower_count",userMapper.selectById(author).getFollowerCount());
        author_list.put("following_count",userMapper.selectById(author).getFollowingCount());
        author_list.put("nickname",userMapper.selectById(author).getNickname());
        author_list.put("author",author);
        author_list.put("avatar_168x168",
                new HashMap<String,Object>(){{
                    put("url_list", Collections.singletonList(avatar));
                    put("width", 720);
                    put("height", 720);
                }}
        );
        author_list.put("avatar_300x300",
                new HashMap<String,Object>(){{
                    put("url_list", Collections.singletonList(avatar));
                    put("width", 720);
                    put("height", 720);
                }}
        );

        author_list.put("cover_url", List.of(
                new HashMap<String, Object>() {{
                    put("url_list", Collections.singletonList("http://sen0fbsqd.hb-bkt.clouddn.com/aTnyHICCi-NMudWfVELeO.png"));
                }}
        ));
        author_list.put("signature", user.getSignature());
        author_list.put("user_age", user.getUserAge());
        author_list.put("gender", user.getGender());
        author_list.put("unique_id", user.getAuthor());
        return ResponseResult.success(author_list);
    }

}
