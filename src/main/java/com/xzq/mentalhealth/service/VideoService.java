package com.xzq.mentalhealth.service;

import com.xzq.mentalhealth.model.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 谢志强
* @description 针对表【video(课程视频)】的数据库操作Service
* @createDate 2023-01-23 22:43:24
*/
public interface VideoService extends IService<Video> {

    Integer addVideo(Video video);

    Integer editVideo(Video video);
}
