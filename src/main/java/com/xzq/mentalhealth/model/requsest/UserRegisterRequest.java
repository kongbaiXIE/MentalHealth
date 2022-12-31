package com.xzq.mentalhealth.model.requsest;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * 用户注册请求库
 * @author kongbai
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -6709046192358303854L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
