package com.simpletiktok.simpletiktok.data.entity;

import com.simpletiktok.simpletiktok.utils.ValidationGroups;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class QueryVideo {
    @NotNull(message = "视频ID不能为空",groups = {ValidationGroups.DeleteValidation.class})
    private String awemeId;

    @NotNull(message = "视频标题不为空", groups = {ValidationGroups.BasicValidation.class})
    private String title;


    @NotNull(message = "author不为空", groups = {ValidationGroups.VideoValidation.class, ValidationGroups.RecommendedValidation.class,ValidationGroups.BasicValidation.class,ValidationGroups.DeleteValidation.class})
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


    @PositiveOrZero(message = "isTop只能是0或1的整数",groups = {ValidationGroups.BasicValidation.class})
    @Digits(integer=1, fraction=0,message = "isTop只能是0或1的整数",groups = {ValidationGroups.BasicValidation.class})
    @NotNull(message = "isTop不为空", groups = {ValidationGroups.BasicValidation.class})
    private Integer isTop;

    @NotNull(message = "pageNo不为空", groups = {ValidationGroups.VideoValidation.class})
    @Min(value = 0, message = "pageNo最小为0", groups = {ValidationGroups.VideoValidation.class})
    private Integer pageNo;

    @NotNull(message = "pageNo不为空", groups = {ValidationGroups.VideoValidation.class, ValidationGroups.RecommendedValidation.class})
    @Min(value = 0, message = "pageNo最小为0", groups = {ValidationGroups.VideoValidation.class, ValidationGroups.RecommendedValidation.class})
    private Integer pageSize;

    @NotNull(message = "start不为空", groups = {ValidationGroups.RecommendedValidation.class})
    @Min(value = 0, message = "start最小为0", groups = {ValidationGroups.RecommendedValidation.class})
    private Integer start;

}
