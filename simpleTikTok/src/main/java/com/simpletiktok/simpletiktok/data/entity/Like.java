package com.simpletiktok.simpletiktok.data.entity;

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
public class Like implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer videoId;

    private String islike;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }
    public String getIslike() {
        return islike;
    }

    public void setIslike(String islike) {
        this.islike = islike;
    }

    @Override
    public String toString() {
        return "Like{" +
                "userId=" + userId +
                ", videoId=" + videoId +
                ", islike=" + islike +
                "}";
    }
}
