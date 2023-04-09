package com.xzq.mentalhealth.model.vo;

import com.xzq.mentalhealth.model.entity.Course;
import lombok.Data;

import java.util.List;

@Data
public class TeacherFrontVO {

    private RoleTeacherVO roleTeacherVO;

    private List<Course> courseList;

}
