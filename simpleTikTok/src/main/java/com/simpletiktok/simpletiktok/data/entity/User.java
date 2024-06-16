package com.simpletiktok.simpletiktok.data.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ricetea
 * @since 2024-06-06
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 抖音号
     */
    @TableId("author")
    private String author;

    private String nickname;

    private String password;

    private String avatar;

    private Integer gender;

    private Integer userAge;

    /**
     * 粉丝数
     */
    private Integer followerCount;

    /**
     * 获赞数
     */
    private Integer awemeCount;

    private Integer follower;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 朋友数
     */
    private Integer followingCount;

    /**
     * 背景图片
     */
    private String coverUrl;

    private Integer version;
    
    @Override
    public String toString() {
        return "User{" +
                "author=" + author +
                ", nickname=" + nickname +
                ", password=" + password +
                ", avatar=" + avatar +
                ", gender=" + gender +
                ", userAge=" + userAge +
                ", followerCount=" + followerCount +
                ", awemeCount=" + awemeCount +
                ", follower=" + follower +
                ", signature=" + signature +
                ", followingCount=" + followingCount +
                ", coverUrl=" + coverUrl +
                "}";
    }
}
