package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Subject;
import com.xzq.mentalhealth.service.SubjectService;
import com.xzq.mentalhealth.mapper.SubjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 谢志强
* @description 针对表【subject(课程科目)】的数据库操作Service实现
* @createDate 2023-01-22 17:27:58
*/
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject>
    implements SubjectService{

    @Resource
    SubjectMapper subjectMapper;

    /**
     * 添加课程分类
     * @param subject
     * @return
     */
    @Override
    public Integer addSubject(Subject subject) {
        if (subject.getTitle()==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"课程分类不能为空");
        }
        return subjectMapper.insert(subject);
    }

    /**
     * 修改菜单
     * @param subject
     * @return
     */
    @Override
    public Integer editSubject(Subject subject) {
        return subjectMapper.updateById(subject);
    }

    @Override
    public List<Subject> findAll(String title) {
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.orderByAsc("sort");
        if (StrUtil.isNotBlank(title)){
            subjectQueryWrapper.like("title",title);
        }
        // 查询所有数据
        List<Subject> subjectList = subjectMapper.selectList(subjectQueryWrapper);
        // 找出pid为null的一级课程分类
        List<Subject> parentNodes = subjectList.stream().filter(subject -> subject.getPid() == null).collect(Collectors.toList());
        // 找出一级课程分类的子分类
        for (Subject parentNode : parentNodes) {
            //筛选所有数据中pid=父级id的数据就是二级菜单
            parentNode.setChildren(subjectList.stream().filter(subject -> parentNode.getId().equals(subject.getPid())).collect(Collectors.toList()));
        }

        return parentNodes;
    }
}




