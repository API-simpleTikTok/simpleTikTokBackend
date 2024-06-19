package com.simpletiktok.simpletiktok.data.mapper;

import com.simpletiktok.simpletiktok.data.entity.Love;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simpletiktok.simpletiktok.data.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ricetea
 * @since 2024-06-06
 */

@Mapper
public interface LoveMapper extends BaseMapper<Love> {
    @Select("SELECT v.* FROM video v " +
            "JOIN love l ON v.aweme_id = l.aweme_id " +
            "WHERE l.author = #{author} AND l.isloved = 'true' " +
            "LIMIT #{page}, #{size}")
    List<Video> getMyLikedVideos(String author, Integer page, Integer size);

    @Select("SELECT l.* FROM love l where l.author = #{author} AND l.aweme_id = #{aweme_id}")
    List<Love> getMyLikedVideosByAuthor(String author, String aweme_id);
}
