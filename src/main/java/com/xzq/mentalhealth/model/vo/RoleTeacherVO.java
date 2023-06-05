package com.xzq.mentalhealth.model.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoleTeacherVO {

    /**
     * id
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String teacherName;
    /**
     * 用户头像
     */
    private String avatarUrl;
    /**
     * 用户简介
     */
    private String intro;
    /**
     * 用户资历（主要是咨询师的相关证件）
     */
    private String career;
}
