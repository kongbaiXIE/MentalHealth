package com.xzq.mentalhealth.model.vo;

import lombok.Data;

@Data
public class PaperFrontVO {

    //问卷id
    private Long paperId;
    //问卷方向
    private String categoryName;
    //问卷封面
    private String paperCover;
    //问卷描述
    private String description;
}
