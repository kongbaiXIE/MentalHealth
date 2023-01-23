package com.xzq.mentalhealth.mapper;

import com.xzq.mentalhealth.model.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【video(课程视频)】的数据库操作Mapper
* @createDate 2023-01-23 22:43:24
* @Entity com.xzq.mentalhealth.model.entity.Video
*/
@Mapper
public interface VideoMapper extends BaseMapper<Video> {

}




