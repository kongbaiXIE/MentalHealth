package com.xzq.mentalhealth.model.vo;

import lombok.Data;

@Data
public class VideoVO {

    private Long id;

    private String title;

    private String videoUrl;//视频id

    private Long isFree;
}
