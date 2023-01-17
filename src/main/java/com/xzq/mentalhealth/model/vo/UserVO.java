package com.xzq.mentalhealth.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xzq.mentalhealth.model.entity.Menu;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 响应给前端的数据 去除敏感数据
 */
@Data
public class UserVO {
    /**
     * id
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;
    /**
     * 角色
     */
    private String role;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态 0 - 正常
     */
    private Integer userStatus;
    /**
     * 用户对应的菜单列表
     */
    private List<Menu> menuList;
    /**
     * token信息
     */
    private String token;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
}
