package com.xzq.mentalhealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzq.mentalhealth.model.entity.Course;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【course(课程)】的数据库操作Mapper
* @createDate 2023-01-22 15:41:47
* @Entity com.xzq.mentalhealth.model.entity.Course
*/
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

}




