package com.xzq.mentalhealth.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 存放图标
 * @TableName dict
 */
@TableName(value ="dict")
@Data
public class Dict implements Serializable {
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
     * 内容
     */
    @TableField(value = "value")
    private String value;

    /**
     * 类型
     */
    @TableField(value = "type")
    private String type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}