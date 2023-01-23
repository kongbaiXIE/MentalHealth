package com.xzq.mentalhealth.mapper;

import com.xzq.mentalhealth.model.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【subject(课程科目)】的数据库操作Mapper
* @createDate 2023-01-22 17:27:58
* @Entity com.xzq.mentalhealth.model.entity.Subject
*/
@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {

}




