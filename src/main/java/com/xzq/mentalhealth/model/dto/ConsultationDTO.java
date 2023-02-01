package com.xzq.mentalhealth.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ConsultationDTO {
    //咨询师名字
    private String name;
    //用户账号名
    private String username;
    //描述主要咨询问题
    private String description;
    //咨询预约时间
    private Date reservationTime;
}
