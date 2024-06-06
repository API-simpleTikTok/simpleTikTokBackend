package com.simpletiktok.simpletiktok.data.entity;

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
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private Integer authorId;

    private Integer like;

    private Integer comment;

    private Integer collect;

    private Integer share;

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
    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }
    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }
    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }
    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
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
            ", like=" + like +
            ", comment=" + comment +
            ", collect=" + collect +
            ", share=" + share +
            ", desc=" + desc +
            ", uploadTime=" + uploadTime +
            ", cover=" + cover +
            ", url=" + url +
        "}";
    }
}
