package com.simpletiktok.simpletiktok.data.mapper;

import com.simpletiktok.simpletiktok.data.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ricetea
 * @since 2024-06-06
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
