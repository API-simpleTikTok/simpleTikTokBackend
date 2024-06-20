package com.simpletiktok.simpletiktok.data.entity;

import com.simpletiktok.simpletiktok.utils.ValidationGroups;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class QueryVideo {
    @Null
    private String awemeId;

    @NotNull(message = "视频标题不为空", groups = {ValidationGroups.BasicValidation.class})
    private String title;


    @NotNull(message = "author不为空", groups = {ValidationGroups.VideoValidation.class, ValidationGroups.RecommendedValidation.class,ValidationGroups.BasicValidation.class})
    private String author;

    @Null
    private Integer diggCount;
    @Null
    private Integer commentCount;
    @Null
    private Integer collectCount;
    @Null
    private Integer shareCount;

    @NotNull(message = "视频描述不为空", groups = {ValidationGroups.BasicValidation.class})
    private String desc;

    @NotNull(message = "日期不为空", groups = {ValidationGroups.BasicValidation.class})
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "Date and time must be in the format yyyy-MM-dd HH:mm:ss", groups = {ValidationGroups.BasicValidation.class})
    private String createTime;

    private String cover;

    @NotNull(message = "url不为空", groups = {ValidationGroups.BasicValidation.class})
    private String url;

    @Min(value = 0, message = "pageSize最小为0", groups = {ValidationGroups.BasicValidation.class})
    @Max(value = 1, message = "pageSize最大为1", groups = {ValidationGroups.BasicValidation.class})
    private Integer isTop;

    @NotNull(message = "pageNo不为空", groups = {ValidationGroups.VideoValidation.class})
    @Min(value = 0, message = "pageNo最小为0", groups = {ValidationGroups.VideoValidation.class})
    private Integer pageNo;

    @NotNull(message = "pageNo不为空", groups = {ValidationGroups.VideoValidation.class, ValidationGroups.RecommendedValidation.class})
    @Min(value = 0, message = "pageNo最小为0", groups = {ValidationGroups.VideoValidation.class, ValidationGroups.RecommendedValidation.class})
    private Integer pageSize;

    @NotNull(message = "pageNo不为空", groups = {ValidationGroups.VideoValidation.class})
    @Min(value = 0, message = "pageNo最小为0", groups = {ValidationGroups.VideoValidation.class})
    private Integer size;

    @NotNull(message = "start不为空", groups = {ValidationGroups.RecommendedValidation.class})
    @Min(value = 0, message = "start最小为0", groups = {ValidationGroups.RecommendedValidation.class})
    private Integer start;

}
