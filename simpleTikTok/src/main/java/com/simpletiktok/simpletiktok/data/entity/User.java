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

    private String avator;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }
    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }
    public Integer getAwemeCount() {
        return awemeCount;
    }

    public void setAwemeCount(Integer awemeCount) {
        this.awemeCount = awemeCount;
    }
    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    public Integer getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }
    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "author=" + author +
                ", nickname=" + nickname +
                ", password=" + password +
                ", avator=" + avator +
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
