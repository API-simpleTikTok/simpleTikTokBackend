package com.simpletiktok.simpletiktok.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZoneOffset;
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

    private String author;

    private Integer diggCount;

    private Integer commentCount;

    private Integer collectCount;

    private Integer shareCount;

    @TableField("`desc`")
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
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
    public long getUploadTime() {
        return uploadTime.toEpochSecond(ZoneOffset.UTC);
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
                ", author=" + author +
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
