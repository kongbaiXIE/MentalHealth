package com.xzq.mentalhealth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Chapter;
import com.xzq.mentalhealth.model.entity.Video;
import com.xzq.mentalhealth.service.VideoService;
import com.xzq.mentalhealth.mapper.VideoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 谢志强
* @description 针对表【video(课程视频)】的数据库操作Service实现
* @createDate 2023-01-23 22:43:24
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{
    @Resource
    VideoMapper videoMapper;
    @Override
    public Integer addVideo(Video video) {
        if (video == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return videoMapper.insert(video);
    }

    @Override
    public Integer editVideo(Video video) {
        return videoMapper.updateById(video);
    }

}




