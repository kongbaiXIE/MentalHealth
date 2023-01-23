package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.mentalhealth.model.entity.Course;

import java.util.List;

/**
* @author 谢志强
* @description 针对表【course(课程)】的数据库操作Service
* @createDate 2023-01-22 15:41:47
*/
public interface CourseService extends IService<Course> {

    Page<Course> courseList(long pageNum, long pageSize, long teacherId,String title);

    List<Course> findAll(String title);

    Integer addCourse(Course course);

    Integer editCourse(Course course);
}
