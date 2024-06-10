package com.simpletiktok.simpletiktok.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private String authorId;

    private Integer diggCount;

    private Integer commentCount;

    private Integer collectCount;

    private Integer shareCount;

    private String desc;

    private LocalDateTime uploadTime;

    private String cover;

    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
    public Integer getDiggCount() {
        return diggCount;
    }

    public void setDiggCount(Integer diggCount) {
        this.diggCount = diggCount;
    }
    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }
    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title=" + title +
                ", authorId=" + authorId +
                ", diggCount=" + diggCount +
                ", commentCount=" + commentCount +
                ", collectCount=" + collectCount +
                ", shareCount=" + shareCount +
                ", desc=" + desc +
                ", uploadTime=" + uploadTime +
                ", cover=" + cover +
                ", url=" + url +
                "}";
    }
}
