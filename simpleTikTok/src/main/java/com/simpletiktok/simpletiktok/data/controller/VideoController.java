package com.simpletiktok.simpletiktok.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.simpletiktok.simpletiktok.data.entity.QueryVideo;
import com.simpletiktok.simpletiktok.data.entity.Video;
import com.simpletiktok.simpletiktok.data.mapper.UserMapper;
import com.simpletiktok.simpletiktok.data.service.BloomFilterService;
import com.simpletiktok.simpletiktok.data.service.IVideoService;
import com.simpletiktok.simpletiktok.utils.ValidationGroups;
import com.simpletiktok.simpletiktok.vo.ResponseResult;
import jakarta.annotation.Resource;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.*;


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

    @Resource
    private UserMapper userMapper;

    @Autowired
    private BloomFilterService bloomFilterService;

    private String avatar;

    @GetMapping("/my")
    public ResponseResult<Map<String, Object>> getMyVideo(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String author) {
        List<Video> videoList = videoService.getMyVideo(pageNo, pageSize, author);
        LambdaQueryWrapper<Video> countQueryWrapper = new LambdaQueryWrapper<>();
        countQueryWrapper.eq(Video::getAuthor, author);
        int totalVideos = (int) videoService.count(countQueryWrapper);
        Map<String, Object> videoPage = new HashMap<>();
        videoPage.put("pageNo", pageNo);
        videoPage.put("total", totalVideos);
        List<Map<String, Object>> newList = new ArrayList<>();
        for (Video video : videoList) {
            avatar = userMapper.selectById(author).getAvatar();
            Map<String, Object> newVideo = new HashMap<>();
            newVideo.put("aweme_id", video.getAwemeId());
            newVideo.put("desc", video.getDesc());
            newVideo.put("create_time", video.getCreateTime().toEpochSecond(ZoneOffset.UTC));

            Map<String, Object> videoDetails = new HashMap<>();
            videoDetails.put("play_addr",
                    new HashMap<String, Object>() {{
                        put("url_list", Collections.singletonList(video.getUrl()));
                        put("height", 1920);
                        put("width", 1080);
                    }}
            );
            videoDetails.put("cover",
                    new HashMap<String, Object>() {{
                        put("url_list", video.getCover());
                        put("height", 720);
                        put("width", 720);
                    }}
            );
            videoDetails.put("aweme_id", video.getAwemeId());
            newVideo.put("video", videoDetails);

            Map<String, Object> statistics = new HashMap<>();
            statistics.put("admire_count", 0);
            statistics.put("comment_count", video.getCommentCount());
            statistics.put("digg_count", video.getDiggCount());
            statistics.put("collect_count", video.getCollectCount());
            statistics.put("play_count", 0);
            statistics.put("share_count", video.getShareCount());
            newVideo.put("statistics", statistics);

            newVideo.put("is_top", video.getIsTop());
            newVideo.put("author_user_id", author);

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
            newVideo.put("author", author_list);
            newList.add(newVideo);
        }
        videoPage.put("list", newList);
        return ResponseResult.success(videoPage);
    }

    @GetMapping("/recommended")
    public ResponseResult<Map<String, Object>> getRecommendedVideo(@ModelAttribute @Validated(ValidationGroups.RecommendedValidation.class) QueryVideo queryVideo) {
        LambdaQueryWrapper<Video> countQueryWrapper = new LambdaQueryWrapper<>();
        int totalVideos = (int) videoService.count(countQueryWrapper);
        List<Video> recommendations = videoService.getRecommendedVideo(queryVideo.getStart(), queryVideo.getPageSize(), queryVideo.getAuthor());
        List<Video> videoList = new ArrayList<>();
        //使用布隆过滤器来避免重复推荐
        while(true){
            for (Video video : recommendations){
                if (!bloomFilterService.mightContain(video.getAwemeId()+queryVideo.getAuthor())) {
                    videoList.add(video);
                    bloomFilterService.add(video.getAwemeId()+queryVideo.getAuthor());
                }
            }
            if(videoList.size() == queryVideo.getPageSize()){
                break;
            }else if(queryVideo.getStart() < totalVideos){
                queryVideo.setStart(queryVideo.getStart()+queryVideo.getPageSize());
                recommendations = videoService.getRecommendedVideo(queryVideo.getStart(), queryVideo.getPageSize()-videoList.size(), queryVideo.getAuthor());
            }else{
                break;
            }
        }
        Map<String, Object> videoPage = new HashMap<>();
        videoPage.put("total", totalVideos);
        List<Map<String,Object>> newList =new ArrayList<>();
        String avatar;
        for(Video video : videoList) {
            avatar = userMapper.selectById(video.getAuthor()).getAvatar();
            Map<String,Object> newVideo = new HashMap<>();
            newVideo.put("desc",video.getDesc());

            Map<String, Object> videoDetails = new HashMap<>();
            videoDetails.put("play_addr", Collections.singletonMap("url_list", Collections.singletonList(video.getUrl())));
            videoDetails.put("poster", "a1.jpg");
            videoDetails.put("ratio", "1080p");
            videoDetails.put("use_static_cover", true);
            videoDetails.put("duration", 13560);
            videoDetails.put("aweme_id", video.getAwemeId());
            newVideo.put("video", videoDetails);

            Map<String, Object> statistics = new HashMap<>();
            statistics.put("admire_count", 0);
            statistics.put("comment_count", video.getCommentCount());
            statistics.put("digg_count", video.getDiggCount());
            statistics.put("collect_count", video.getCollectCount());
            statistics.put("play_count", 0);
            statistics.put("share_count", video.getShareCount());
            newVideo.put("statistics", statistics);

            Map<String, Object> authors = new HashMap<>();
            authors.put("avatar_168x168", Collections.singletonMap("url_list", Collections.singletonList(avatar)));
            authors.put("avatar_300x300", Collections.singletonMap("url_list", Collections.singletonList(avatar)));
            authors.put("cover_url", Arrays.asList(
                    new HashMap<String, Object>() {{
                        put("uri", "douyin-user-image-file/f2196ddaa37f3097932d8a29ff0d0ca5");
                        put("url_list", Collections.singletonList("AiIEMkIA7Cb3s5c4e7e6g.png"));
                    }},
                    new HashMap<String, Object>() {{
                        put("uri", "c8510002be9a3a61aad2");
                        put("url_list", Collections.singletonList("aHzLr77vcdBMUil15rXBa.png"));
                    }}
            ));
            newVideo.put("author", authors);

            newList.add(newVideo);
        }
        videoPage.put("list", newList);
        return ResponseResult.success(videoPage);
    }

    @GetMapping("/like")
    public ResponseResult<Map<String, Object>> getLikeVideo(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String author) {
        List<Video> videoList = videoService.getMyLikedVideos(pageNo, pageSize, author);
        int totalVideos = videoList.size();
        Map<String, Object> videoPage = new HashMap<>();
        videoPage.put("pageNo", pageNo);
        videoPage.put("total", totalVideos);
        List<Map<String, Object>> newList = new ArrayList<>();
        for (Video video : videoList) {
            avatar = userMapper.selectById(author).getAvatar();
            Map<String, Object> newVideo = new HashMap<>();
            newVideo.put("aweme_id", video.getAwemeId());
            newVideo.put("desc", video.getDesc());
            newVideo.put("create_time", video.getCreateTime().toEpochSecond(ZoneOffset.UTC));

            Map<String, Object> videoDetails = new HashMap<>();
            videoDetails.put("play_addr",
                    new HashMap<String, Object>() {{
                        put("url_list", Collections.singletonList(video.getUrl()));
                        put("height", 1920);
                        put("width", 1080);
                    }}
            );
            videoDetails.put("cover",
                    new HashMap<String, Object>() {{
                        put("url_list", video.getCover());
                        put("height", 720);
                        put("width", 720);
                    }}
            );
            videoDetails.put("aweme_id", video.getAwemeId());
            newVideo.put("video", videoDetails);

            Map<String, Object> statistics = new HashMap<>();
            statistics.put("admire_count", 0);
            statistics.put("comment_count", video.getCommentCount());
            statistics.put("digg_count", video.getDiggCount());
            statistics.put("collect_count", video.getCollectCount());
            statistics.put("play_count", 0);
            statistics.put("share_count", video.getShareCount());
            newVideo.put("statistics", statistics);

            newVideo.put("is_top", video.getIsTop());
            newVideo.put("author_user_id", author);

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
            newVideo.put("author", author_list);
            newList.add(newVideo);
        }
        videoPage.put("list", newList);
        return ResponseResult.success(videoPage);
    }

    @PostMapping("/upload")
    public ResponseResult<Map<String, String>> uploadVideo(@RequestBody @Validated(ValidationGroups.BasicValidation.class) QueryVideo queryVideo) {
        UUID uuid = UUID.randomUUID();
        queryVideo.setAwemeId(uuid.toString());
        Video video = new Video();
        try {
            BeanUtils.copyProperties(video, queryVideo);
            video.setDiggCount(0);
            video.setShareCount(0);
            video.setCollectCount(0);
            video.setCommentCount(0);
        } catch (Exception e) {
            System.out.println(e.toString());  // 处理可能的异常
        }

        boolean success = videoService.save(video);
        Map<String, String> res = new HashMap<>();
        if(success){
            res.put("msg","成功");
            return ResponseResult.success(res);
        }else{
            return ResponseResult.failure(402,"上传失败");
        }
    }
}
