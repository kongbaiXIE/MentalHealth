package com.xzq.mentalhealth.model.dto;

import lombok.Data;

@Data
public class PaperDTO {

    private Integer type1; //选择题数量
    private Integer type2; //判断题数量
    private Long paperId; // 问卷id
    private Long categoryId; //分类id

}
