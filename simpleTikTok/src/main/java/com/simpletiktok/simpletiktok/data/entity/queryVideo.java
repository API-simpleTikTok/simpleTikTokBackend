package com.simpletiktok.simpletiktok.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.simpletiktok.simpletiktok.utils.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class queryVideo {

    private String awemeId;

    @NotNull
    @Pattern(regexp = "\\p{IsHan}*", message = "请输入汉字", groups = {ValidationGroups.VideoValidation.class})
    private String title;

    @NotNull
    private String author;

    private Integer diggCount;

    private Integer commentCount;

    private Integer collectCount;

    private Integer shareCount;

    private String desc;

    private LocalDateTime createTime;

    private String cover;

    private String url;

    private Integer isTop;
}
