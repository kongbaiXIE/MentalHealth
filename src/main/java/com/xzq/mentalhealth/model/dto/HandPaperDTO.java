package com.xzq.mentalhealth.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class HandPaperDTO {

    //题目id集合
    private List<Long> handQuestionIds;
    private Long paperId;

}
