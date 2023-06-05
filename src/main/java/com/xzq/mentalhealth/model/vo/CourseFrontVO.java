package com.xzq.mentalhealth.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseFrontVO {
    /**
     * 发布课程咨询师id
     */
    private Long userId;
    /**
     * 课程专业id
     */
    private Long subjectId;

    /**
     * 课程专业父级id
     */
    private Long subjectPid;

    /**
     * 课程标题
     */
    private String title;

    /**
     * 课程简介
     */
    private String description;

    /**
     * 课程销售价格，设置为0则可免费观看
     */
    private BigDecimal price;

    /**
     * 总课时
     */
    private Integer lessonNum;

    /**
     * 课程封面
     */
    private String cover;

    /**
     * 销售数量
     */
    private Long buyCount;

    /**
     * 浏览数量
     */
    private Long viewCount;

    private String teacherName;
    //咨询师头像
    private String avatar;

    private String subjectParentTitle;

    private String subjectTitle;

    private Boolean buyCourse;

}
