package com.xzq.mentalhealth.service;

import com.xzq.mentalhealth.model.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 谢志强
* @description 针对表【subject(课程科目)】的数据库操作Service
* @createDate 2023-01-22 17:27:58
*/
public interface SubjectService extends IService<Subject> {

    List<Subject> findAll(String name);

    Integer addSubject(Subject subject);

    Integer editSubject(Subject subject);
}
