package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.model.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.mentalhealth.model.vo.TeacherFrontVO;
import io.swagger.models.auth.In;

import java.util.List;

/**
* @author 谢志强
* @description 针对表【teacher(讲师表)】的数据库操作Service
* @createDate 2023-01-22 15:59:38
*/
public interface TeacherService extends IService<Teacher> {

    Page<Teacher> teacherList(long pageNum, long pageSize, String name, Integer level);

    List<Teacher> findAll(String name);
    List<Teacher> findAllByLimit(String name);
    Integer addTeacher(Teacher teacher);

    Integer editTeacher(Teacher teacher);

    TeacherFrontVO getTeacherFrontInfo(long teacherId);
}
