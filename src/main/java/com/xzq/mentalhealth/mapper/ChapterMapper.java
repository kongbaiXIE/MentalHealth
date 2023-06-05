package com.xzq.mentalhealth.mapper;

import com.xzq.mentalhealth.model.entity.Chapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【chapter(课程章节)】的数据库操作Mapper
* @createDate 2023-01-23 21:23:19
* @Entity com.xzq.mentalhealth.model.entity.Chapter
*/
@Mapper
public interface ChapterMapper extends BaseMapper<Chapter> {

}




