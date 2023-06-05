package com.xzq.mentalhealth.model.vo;

import lombok.Data;

@Data
public class ResultVO {
    //测量表名
    private String paperName;
    //最终得分
    private int totalScore;
    //结果分析
    private String result;

}
