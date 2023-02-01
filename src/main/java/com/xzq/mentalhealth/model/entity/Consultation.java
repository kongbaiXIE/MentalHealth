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
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 预约用户名
     */
    private String username;

    /**
     * 预约咨询师名字
     */
    private String teacherName;

    /**
     * 描述主要咨询的问题
     */
    private String description;

    /**
     * 预约咨询时间
     */
    private Date reservationTime;

    /**
     * 咨询开始时间
     */
    private Date startTime;

    /**
     * 咨询结束时间
     */
    private Date endTime;

    /**
     * 咨询师分析
     */
    private String consultAbout;

    /**
     * 预约状态（0：预约中 1：接受预约 2：拒绝预约 3：咨询完成）
     */
    private Integer consultationStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}