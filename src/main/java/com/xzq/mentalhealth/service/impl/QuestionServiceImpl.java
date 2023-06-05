package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Question;
import com.xzq.mentalhealth.service.QuestionService;
import com.xzq.mentalhealth.mapper.QuestionMapper;
import com.xzq.mentalhealth.untils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
* @author 谢志强
* @description 针对表【question(测评题目表)】的数据库操作Service实现
* @createDate 2023-01-17 19:23:43
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

    @Resource
    QuestionMapper questionMapper;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<Question> QuestionList(long pageNum, long pageSize, String name) {
        Page<Question> questionPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.orderByAsc("id");
        if (!"".equals(name)) {
            questionQueryWrapper.like("name", name);
        }
        return questionMapper.selectPage(questionPage, questionQueryWrapper);
    }

    @Override
    public List<Question> findAll(String name) {
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)){
            questionQueryWrapper.like("name",name);
        }

        return questionMapper.selectList(questionQueryWrapper);
    }

    /**
     * 添加测评题目
     * @param question
     * @return
     */
    @Override
    public Integer addQuestion(Question question) {
        String name = question.getName();
        if (name == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目不能为空");
        }
        Long userId = Objects.requireNonNull(TokenUtils.getUser()).getId();
        question.setUserId(userId);
        return questionMapper.insert(question);
    }

    /**
     * 修改测评题目
     * @param question
     * @return
     */
    @Override
    public Integer editQuestion(Question question) {
        return questionMapper.updateById(question);
    }
}




