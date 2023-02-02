package com.xzq.mentalhealth.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 测评问卷表
 * @TableName paper
 */
@TableName(value ="paper")
@Data
public class Paper implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;
    /**
     * 测评问卷解析
     */
    @TableField(value = "paperCover")
    private String paperCover;
    /**
     * 测评问卷解析
     */
    @TableField(value = "result")
    private String result;

    /**
     * 问卷描述
     */
    @TableField(value = "description")
    private String description;
    /**
     * 题目分类id
     */
    @TableField(value = "categoryId")
    private Long categoryId;
    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "updateTime")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "isDelete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}