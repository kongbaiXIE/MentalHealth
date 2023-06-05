package com.xzq.mentalhealth.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 咨询表
 * @TableName consultation
 */
@TableName(value ="consultation")
@Data
public class Consultation implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户角色为咨询师的用户id
     */
    @TableField(value = "teacherId")
    private Long teacherId;

    /**
     * 用户角色是普通用户的用户id
     */
    @TableField(value = "userId")
    private Long userId;

    /**
     * 预约用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 预约咨询师名字
     */
    @TableField(value = "teacherName")
    private String teacherName;

    /**
     * 描述主要咨询的问题
     */
    @TableField(value = "description")
    private String description;

    /**
     * 预约咨询时间
     */
    @TableField(value = "reservationTime")
    private Date reservationTime;

    /**
     * 咨询开始时间
     */
    @TableField(value = "startTime")
    private Date startTime;

    /**
     * 咨询结束时间
     */
    @TableField(value = "endTime")
    private Date endTime;

    /**
     * 咨询师分析
     */
    @TableField(value = "consultAbout")
    private String consultAbout;

    /**
     * 预约状态（0：预约中 1：接受预约 2：拒绝预约 3：咨询完成）
     */
    @TableField(value = "consultationStatus")
    private Integer consultationStatus;

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