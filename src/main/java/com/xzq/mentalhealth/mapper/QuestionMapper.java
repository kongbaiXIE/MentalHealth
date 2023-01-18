package com.xzq.mentalhealth.mapper;

import com.xzq.mentalhealth.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【question(测评题目表)】的数据库操作Mapper
* @createDate 2023-01-17 19:23:42
* @Entity com.xzq.mentalhealth.model.entity.Question
*/
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}




