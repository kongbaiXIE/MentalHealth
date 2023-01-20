package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.PaperMapper;
import com.xzq.mentalhealth.model.dto.ResultDTO;
import com.xzq.mentalhealth.model.entity.Paper;
import com.xzq.mentalhealth.model.entity.Question;
import com.xzq.mentalhealth.model.entity.Result;
import com.xzq.mentalhealth.model.vo.ResultVO;
import com.xzq.mentalhealth.service.PaperService;
import com.xzq.mentalhealth.service.ResultService;
import com.xzq.mentalhealth.mapper.ResultMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
* @author 谢志强
* @description 针对表【result(测评结果表)】的数据库操作Service实现
* @createDate 2023-01-20 19:47:11
*/
@Service
public class ResultServiceImpl extends ServiceImpl<ResultMapper, Result>
    implements ResultService{
    @Resource
    ResultMapper resultMapper;
    @Resource
    PaperService paperService;
    @Resource
    PaperMapper paperMapper;
    @Override
    public Page<Result> resultList(long pageNum, long pageSize, String name) {
        Page<Result> resultPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Result> resultQueryWrapper = new QueryWrapper<>();
        resultQueryWrapper.orderByAsc("id");
        if (!"".equals(name)) {
            resultQueryWrapper.like("name", name);
        }
        return resultMapper.selectPage(resultPage, resultQueryWrapper);
    }

    @Override
    public List<Result> findAll(String name) {
        QueryWrapper<Result> resultQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)){
            resultQueryWrapper.like("name",name);
        }

        return resultMapper.selectList(resultQueryWrapper);
    }

    @Override
    public Integer editResult(Result result) {
        return resultMapper.updateById(result);
    }

    /**
     * 通过比较学生提交的问卷得出结果
     * @param resultDTO
     * @return
     */
    @Override
    public ResultVO addStuResult(ResultDTO resultDTO) {
        Long paperId = resultDTO.getPaperId();
        Long userId = resultDTO.getUserId();
        //todo 防止重复添加相同的问卷 在创建之前删除
        UpdateWrapper<Result> resultUpdateWrapper = new UpdateWrapper<>();
        resultUpdateWrapper.eq("userId",userId);
        int delete = resultMapper.delete(resultUpdateWrapper);
        if (delete <0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        int totalScore = 0;
        //学生提交的问卷
        List<Question> StuQuestions = resultDTO.getAddQuestion();
        //通过问卷id查询出题库中的问卷
        List<Question> RightQuestionList = paperService.viewPaper(paperId);
        for (int i=0;i<StuQuestions.size();i++){
            if (Objects.equals(StuQuestions.get(i).getAnswer(), RightQuestionList.get(i).getAnswer())){
                totalScore+=RightQuestionList.get(i).getScore();
            }
        }
        //通过paperId查询出问卷表的信息
        Paper paper = paperMapper.selectById(paperId);
        Result result = new Result();
        //将用户id 用户最终得分和测评题目存到结果表
        result.setScore(totalScore);
        result.setName(paper.getName());
        result.setUserId(userId);
        resultMapper.insert(result);
        //返回给用户测评结果数据
        ResultVO resultVO = new ResultVO();
        resultVO.setPaperName(paper.getName());
        resultVO.setTotalScore(totalScore);
        resultVO.setResult(paper.getResult());
        return resultVO;
    }
}
