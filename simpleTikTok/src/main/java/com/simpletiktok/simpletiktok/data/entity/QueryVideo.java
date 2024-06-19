package com.simpletiktok.simpletiktok.data.entity;

import com.simpletiktok.simpletiktok.utils.ValidationGroups;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QueryVideo {

    private String awemeId;

    @NotNull(message = "视频标题不为空", groups = {ValidationGroups.BasicValidation.class})
    private String title;


    @NotNull(message = "author不为空", groups = {ValidationGroups.VideoValidation.class, ValidationGroups.RecommendedValidation.class,ValidationGroups.BasicValidation.class})
    private String author;

    private Integer diggCount;

    private Integer commentCount;

    private Integer collectCount;

    private Integer shareCount;

    @NotNull(message = "视频描述不为空", groups = {ValidationGroups.BasicValidation.class})
    private String desc;

    @NotNull(message = "日期不为空", groups = {ValidationGroups.BasicValidation.class})
    @Past(message = "日期必须是之前的时间", groups = {ValidationGroups.BasicValidation.class})
    private LocalDateTime createTime;

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

    @NotNull(message = "pageNo不为空", groups = {ValidationGroups.VideoValidation.class, ValidationGroups.RecommendedValidation.class})
    @Min(value = 0, message = "pageNo最小为0", groups = {ValidationGroups.VideoValidation.class, ValidationGroups.RecommendedValidation.class})
    private Integer size;

}
