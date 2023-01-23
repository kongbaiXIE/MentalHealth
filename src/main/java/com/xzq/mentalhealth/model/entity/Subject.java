package com.xzq.mentalhealth.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 课程科目
 * @TableName subject
 */
@TableName(value ="subject")
@Data
public class Subject implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 课程科目
     */
    private String title;

    /**
     * 父id
     */
    private Long pid;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private List<Subject> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}