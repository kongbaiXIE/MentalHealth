package com.xzq.mentalhealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzq.mentalhealth.model.entity.QuestionPaper;
import org.apache.ibatis.annotations.Mapper;


/**
* @author 谢志强
* @description 针对表【question_paper(题目和测评问卷关系表)】的数据库操作Mapper
* @createDate 2023-01-18 11:55:44
* @Entity generator.domain.QuestionPaper
*/
@Mapper
public interface QuestionPaperMapper extends BaseMapper<QuestionPaper> {

}




