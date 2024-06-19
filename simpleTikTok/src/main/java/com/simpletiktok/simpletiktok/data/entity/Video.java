package com.simpletiktok.simpletiktok.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotNull;
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

    /**
     * 视频ID
     */
    @TableId("aweme_id")
    private String awemeId;

    private String title;

    @NotNull
    private String author;

    private Integer diggCount;

    private Integer commentCount;

    private Integer collectCount;

    private Integer shareCount;

    @TableField("`desc`")
    private String desc;

    private LocalDateTime createTime;

    private String cover;

    private String url;

    private Integer isTop;

    public String getAwemeId() {
        return awemeId;
    }

    public void setAwemeId(String awemeId) {
        this.awemeId = awemeId;
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
    public long getCreateTime() {
        return createTime.toEpochSecond(ZoneOffset.UTC);
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
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
    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    @Override
    public String toString() {
        return "Video{" +
                "awemeId=" + awemeId +
                ", title=" + title +
                ", author=" + author +
                ", diggCount=" + diggCount +
                ", commentCount=" + commentCount +
                ", collectCount=" + collectCount +
                ", shareCount=" + shareCount +
                ", desc=" + desc +
                ", createTime=" + createTime +
                ", cover=" + cover +
                ", url=" + url +
                ", isTop=" + isTop +
                "}";
    }
}
