package com.xzq.mentalhealth.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 测评题目表
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
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
     * 类型：1.选择 2.判断 3.问答
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * a选项
     */
    @TableField(value = "optionA")
    private String optionA;

    /**
     * b选项
     */
    @TableField(value = "optionB")
    private String optionB;

    /**
     * c选项
     */
    @TableField(value = "optionC")
    private String optionC;

    /**
     * d选项
     */
    @TableField(value = "optionD")
    private String optionD;

    /**
     * 分数
     */
    @TableField(value = "score")
    private Integer score;

    /**
     * 答案
     */
    @TableField(value = "answer")
    private String answer;

    /**
     * 出题人Id
     */
    @TableField(value = "userId")
    private Long userId;
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
     * 更新时间
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