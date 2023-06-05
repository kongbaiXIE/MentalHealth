package com.xzq.mentalhealth.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 课程视频
 * @TableName video
 */
@TableName(value ="video")
@Data
public class Video implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 章节id
     */
    private Long chapterId;

    /**
     * 节点名称
     */
    private String title;

    /**
     * 课程视频本地路径
     */
    private String videoUrl;

    /**
     * 播放次数
     */
    private Long play_count;

    /**
     * 是否可以试听：0收费 1免费
     */
    private Long isFree;
    /**
     * 排序
     */
    private Object sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}