package com.simpletiktok.simpletiktok.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.zxing.WriterException;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.simpletiktok.simpletiktok.data.entity.Love;
import com.simpletiktok.simpletiktok.data.entity.QueryVideo;
import com.simpletiktok.simpletiktok.data.entity.User;
import com.simpletiktok.simpletiktok.data.entity.Video;
import com.simpletiktok.simpletiktok.data.mapper.UserMapper;
import com.simpletiktok.simpletiktok.data.service.ILoveService;
import com.simpletiktok.simpletiktok.data.service.IUserService;
import com.simpletiktok.simpletiktok.data.service.IVideoService;
import com.simpletiktok.simpletiktok.utils.GoogleAuthenticationTool;
import com.simpletiktok.simpletiktok.utils.JwtUtils;
import com.simpletiktok.simpletiktok.utils.ValidationGroups;
import com.simpletiktok.simpletiktok.vo.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


import java.io.IOException;
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
    private ILoveService loveService;
    @Resource
    private IVideoService VideoService;
    @Resource
    private IUserService UserService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/sign")
    public ResponseResult<?> sign(@RequestBody @Validated(ValidationGroups.UserValidation.class) User user)
    {
        String author = user.getAuthor();
        String password = user.getPassword();

        return UserService.register(author, password);
    }

    @PostMapping("/diggVideo")
    public ResponseResult<Object> diggVideo(@RequestBody Map params) {
        Love love = new Love();
        love.setAuthor((String) params.get("author"));
        love.setAwemeId((String) params.get("aweme_id"));
        love.setIsloved(String.valueOf(params.get("isLoved")));
        if(love.getAuthor().isEmpty() || love.getAwemeId().isEmpty() || love.getIsloved().isEmpty() || love.getAuthor() == null || love.getAwemeId() == null || love.getIsloved() == null){
            return ResponseResult.failure(403,"缺少必要参数");
        }
        List<Love> loveList  = loveService.getLoveByAuthor(love.getAuthor(),love.getAwemeId());
        Map<String, Object> res = new HashMap<>();
        if(!loveList.isEmpty()){
            boolean isUpdate = loveService.updateLoveStatus(love.getAuthor(),love.getIsloved(),love.getAwemeId());
            if(!isUpdate){
                res.put("code",400);
                res.put("msg","更新失败");
            }else{
                res.put("code",200);
                res.put("msg","更新成功");
            }
            return ResponseResult.success(res);
        }else {
            boolean isSaved = loveService.save(love);
            if (isSaved) {
                res.put("code",200);
                res.put("msg","更新成功");
            } else {
                res.put("code",400);
                res.put("msg","更新失败");
            }
            return ResponseResult.success(res);
        }
    }

    @DeleteMapping("/deleteVideo")
    public ResponseResult<Boolean> deleteVideo(
            @RequestHeader("Authorization") String token,
            @ModelAttribute @Validated(ValidationGroups.DeleteValidation.class) QueryVideo queryVideo) {

        String subject = JwtUtils.getSubject(token);

        if (!hasPermission(subject, queryVideo.getAuthor(), queryVideo.getAwemeId())) {
            return ResponseResult.failure(403, "用户没有权限删除该视频");
        }

        boolean isRemoved = VideoService.removeById(queryVideo.getAwemeId());
        if (isRemoved) {
            return ResponseResult.success(true);
        } else {
            return ResponseResult.failure(400, "删除视频失败");
        }
    }

    public boolean hasPermission(String subject, String author, String aweme_id) {

        if(!subject.equals(author))
        {
            return false;
        }

        Video video = VideoService.getById(aweme_id);
        if (video == null) {
            return false;
        }
        // 验证当前用户是否是视频的作者
        return video.getAuthor().equals(author);
    }

    @GetMapping("/getUploadToken")
    public Object getUploadToken(@RequestParam String author) {
        if(author == null || author.isEmpty()) {
            return ResponseResult.failure(403,"缺少必要参数");
        }
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
        if(author == null || author.isEmpty()) {
            return ResponseResult.failure(403,"缺少必要参数");
        }
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

    @GetMapping("/bindingGoogleTwoFactorValidate")
    public ResponseResult<Map<String, Object>> bindingGoogleTwoFactorValidate(@RequestParam String author) {
        if(author == null || author.isEmpty()) {
            return ResponseResult.failure(403,"缺少必要参数");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author", author);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null) {
            return ResponseResult.failure(401,"用户不存在");
        }
        Map<String,Object> map = new HashMap<>();
        if(user.getTwoFactorCode()!=null&& !user.getTwoFactorCode().isEmpty()){
            return ResponseResult.failure(403,"该用户已经绑定了，不可重复绑定，若不慎删除令牌，请联系管理员重置");
        }

        String[] res = generateQRCode(author);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("author", author).set("twoFactorCode", res[1]);
        userMapper.update(null,updateWrapper);
        map.put("img", res[0]);
        return ResponseResult.success(map);
    }

    private String[] generateQRCode(String author) {
        String randomSecretKey = GoogleAuthenticationTool.generateSecretKey();
        //此步设置的参数就是App扫码后展示出来的参数
        String qrCodeString = GoogleAuthenticationTool.spawnScanQRString(author,randomSecretKey,"Simpletiktok");
        String qrCodeImageBase64 = null;
        try {
            qrCodeImageBase64 = GoogleAuthenticationTool.createQRCode(qrCodeString,null,512,512);
        } catch (WriterException | IOException e) {
            System.out.println(e.toString());
        }

        return new String[]{qrCodeImageBase64, randomSecretKey};
    }

}
