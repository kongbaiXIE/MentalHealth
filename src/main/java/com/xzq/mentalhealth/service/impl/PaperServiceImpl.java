package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.PaperMapper;
import com.xzq.mentalhealth.mapper.QuestionMapper;
import com.xzq.mentalhealth.mapper.QuestionPaperMapper;
import com.xzq.mentalhealth.model.dto.HandPaperDTO;
import com.xzq.mentalhealth.model.dto.PaperDTO;
import com.xzq.mentalhealth.model.entity.Paper;
import com.xzq.mentalhealth.model.entity.Question;
import com.xzq.mentalhealth.model.entity.QuestionPaper;
import com.xzq.mentalhealth.service.PaperService;
import com.xzq.mentalhealth.service.QuestionPaperService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 谢志强
* @description 针对表【paper(测评问卷表)】的数据库操作Service实现
* @createDate 2023-01-18 11:55:41
*/
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper>
    implements PaperService {

    @Resource
    PaperMapper paperMapper;
    @Resource
    QuestionMapper questionMapper;
    @Resource
    QuestionPaperMapper questionPaperMapper;
    @Resource
    QuestionPaperService questionPaperService;

    @Override
    public Page<Paper> PaperList(long pageNum, long pageSize, String name) {
        Page<Paper> PaperPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        paperQueryWrapper.orderByAsc("id");
        if (!"".equals(name)) {
            paperQueryWrapper.like("name", name);
        }
        return paperMapper.selectPage(PaperPage, paperQueryWrapper);
    }

    @Override
    public List<Paper> findAll(String name) {
        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)){
            paperQueryWrapper.like("name",name);
        }

        return paperMapper.selectList(paperQueryWrapper);
    }

    @Override
    public Integer addPaper(Paper paper) {
        String paperName = paper.getName();
        if (paperName == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"名字不能为空");
        }
        return paperMapper.insert(paper);
    }

    @Override
    public Integer editPaper(Paper paper) {
        return paperMapper.updateById(paper);
    }
    //创建测评问卷
    @Override
    public Boolean takePaper(PaperDTO paperDTO) {
        //todo 防止重复添加相同的问卷 在创建之前删除
        UpdateWrapper<QuestionPaper> questionPaperUpdateWrapper = new UpdateWrapper<>();
        questionPaperUpdateWrapper.eq("paperId",paperDTO.getPaperId());
        int delete = questionPaperMapper.delete(questionPaperUpdateWrapper);
        if (delete <0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        // 通过题目分类categoryId查询出该类型下的所有题目
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("categoryId",paperDTO.getCategoryId());
        // 根据categoryId所有题目,再通过type区分题型
        List<Question> questions = questionMapper.selectList(questionQueryWrapper);
        List<Question> type1List = questions.stream().filter(question -> question.getType()==1).collect(Collectors.toList());//选择题
        List<Question> type2List = questions.stream().filter(question -> question.getType()==2).collect(Collectors.toList());//判断题
        // 查看题库中是否这么多数量的题型
        if (type1List.size() < paperDTO.getType1()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"题库中选择题不足");
        }
        if (type2List.size() < paperDTO.getType2()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"题库中判断题不足");
        }
        // 开始随机组卷
        List<QuestionPaper> questionPapers = getQuestionPaper(type1List.size(), paperDTO.getType1(), type1List, paperDTO.getPaperId());
        questionPapers.addAll(getQuestionPaper(type2List.size(), paperDTO.getType2(), type2List, paperDTO.getPaperId()));
        //批量插入关联关系表
        return questionPaperService.saveBatch(questionPapers);
    }

    /**
     * 手动创建问卷
     * @param handPaperDTO
     * @return
     */
    @Override
    public Boolean handPaper(HandPaperDTO handPaperDTO) {
        //todo 防止重复添加相同的问卷 在创建之前删除
        UpdateWrapper<QuestionPaper> questionPaperUpdateWrapper = new UpdateWrapper<>();
        questionPaperUpdateWrapper.eq("paperId",handPaperDTO.getPaperId());
        int delete = questionPaperMapper.delete(questionPaperUpdateWrapper);
        if (delete <0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<Long> handQuestionIds = handPaperDTO.getHandQuestionIds();
        ArrayList<QuestionPaper> list = new ArrayList<>();
        for (Long handQuestionId : handQuestionIds) {
            QuestionPaper questionPaper = new QuestionPaper();
            questionPaper.setQuestionId(handQuestionId);
            questionPaper.setPaperId(handPaperDTO.getPaperId());
            list.add(questionPaper);
        }
        return questionPaperService.saveBatch(list);
    }

    /**
     * 查看问卷
     * @param paperId
     * @return
     */
    //todo 现在实后端看的问卷，到时候前端需要进行脱敏
    @Override
    public List<Question> viewPaper(Long paperId) {
        QueryWrapper<QuestionPaper> questionPaperQueryWrapper = new QueryWrapper<>();
        questionPaperQueryWrapper.eq("paperId",paperId);
        // 通过问卷id查询出相关题目的关联数组
        List<QuestionPaper> questionPaperList = questionPaperMapper.selectList(questionPaperQueryWrapper);
        List<Long> questionIds = new ArrayList<>();
        for (QuestionPaper questionPaper : questionPaperList) {
            questionIds.add(questionPaper.getQuestionId());
        }
        return questionMapper.selectBatchIds(questionIds);
    }
    /**
     * 查看问卷
     * @param paperId
     * @return
     */
    //todo 现在实后端看的问卷，到时候前端需要进行脱敏
    @Override
    public List<Question> safeViewPaper(Long paperId) {
        QueryWrapper<QuestionPaper> safeQueryWrapper = new QueryWrapper<>();
        safeQueryWrapper.eq("paperId",paperId);
        // 通过问卷id查询出相关题目的关联数组
        List<QuestionPaper> questionPaperList = questionPaperMapper.selectList(safeQueryWrapper);
        List<Long> questionIds = new ArrayList<>();
        for (QuestionPaper questionPaper : questionPaperList) {
            questionIds.add(questionPaper.getQuestionId());
        }
        List<Question> questionList = questionMapper.selectBatchIds(questionIds);
        for (Question question : questionList) {
            question.setAnswer("");
        }
        return questionList;
    }


    /**
     * 获取题库与问卷的关联关系数组
     * @param questionSize 题库中该题目数量
     * @param paperQuestionSize 需要的题目数量
     * @param source 题库中不同类型数组
     * @param paperId 问卷id
     * @return
     */
    private List<QuestionPaper> getQuestionPaper(int questionSize,int paperQuestionSize,List<Question> source,Long paperId){
        // 通过题库中的题目数量和需要生成的题目数量产生随机数 第一个参数为题库题目数量 第二参数为传递的需要的题目数量
        List<Integer> typeRandomList = getEleList(questionSize, paperQuestionSize);
        ArrayList<QuestionPaper> questionPaperList = new ArrayList<>();
        for (Integer index : typeRandomList) {
            Question question = source.get(index);
            QuestionPaper questionPaper = new QuestionPaper();
            questionPaper.setPaperId(paperId);
            questionPaper.setQuestionId(question.getId());
            questionPaperList.add(questionPaper);
        }
        return questionPaperList;
    }

    /**
     * 封装一个获取随机数的方法
     * @param sourceSize 原始数组的长度
     * @param resultSize 获取指定长度的数组
     * @return
     */
    private List<Integer> getEleList(int sourceSize,int resultSize){
        List<Integer> list = CollUtil.newArrayList();
        for(int i=0;i<sourceSize;i++){
            list.add(i);
        }
        return RandomUtil.randomEleList(list,resultSize);
    }
}




