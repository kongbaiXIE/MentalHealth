package com.xzq.mentalhealth.model.dto;

import lombok.Data;

@Data
public class AliPay {
    //自己生成的订单号
    private String traceNo;
    //订单金额
    private double totalAmount;
    //订单名称 一般用课程标题
    private String subject;
    private String alipayTraceNo;
}
