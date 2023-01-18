package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 谢志强
* @description 针对表【question(测评题目表)】的数据库操作Service
* @createDate 2023-01-17 19:23:43
*/
public interface QuestionService extends IService<Question> {

    Page<Question> QuestionList(long pageNum, long pageSize, String name);

    List<Question> findAll(String name);

    Integer addQuestion(Question question);

    Integer editQuestion(Question question);
}
