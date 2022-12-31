package com.xzq.mentalhealth.model.requsest;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 6078467734264299461L;

    private String userAccount;
    private String userPassword;
}
