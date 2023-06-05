package com.xzq.mentalhealth.model.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVO {

    private Long id;

    private String title;

    //表示小节
    private List<VideoVO> children = new ArrayList<>();
}
