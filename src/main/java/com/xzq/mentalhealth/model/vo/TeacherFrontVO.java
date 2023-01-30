package com.xzq.mentalhealth.model.vo;

import com.xzq.mentalhealth.model.entity.Course;
import com.xzq.mentalhealth.model.entity.Teacher;
import lombok.Data;

import java.util.List;

@Data
public class TeacherFrontVO {

    private Teacher teacher;

    private List<Course> courseList;

}
