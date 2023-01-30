package com.xzq.mentalhealth.model.dto;


import lombok.Data;

import java.util.Date;

@Data
public class ReservationDTO {

    //咨询师名字
    private String name;
    //用户账号名
    private String userAccount;
    //描述主要咨询时间
    private String description;

    private Date reservationTime;

}
