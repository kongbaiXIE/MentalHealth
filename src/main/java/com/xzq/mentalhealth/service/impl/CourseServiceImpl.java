package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.SubjectMapper;
import com.xzq.mentalhealth.mapper.TeacherMapper;
import com.xzq.mentalhealth.model.entity.Course;
import com.xzq.mentalhealth.model.entity.Subject;
import com.xzq.mentalhealth.model.entity.Teacher;
import com.xzq.mentalhealth.service.CourseService;
import com.xzq.mentalhealth.mapper.CourseMapper;
import com.xzq.mentalhealth.untils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
* @author 谢志强
* @description 针对表【course(课程)】的数据库操作Service实现
* @createDate 2023-01-22 15:41:47
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

    @Resource
    CourseMapper courseMapper;
    @Resource
    TeacherMapper teacherMapper;
    @Resource
    SubjectMapper subjectMapper;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param teacherId
     * @param title
     * @return
     */
    @Override
    public Page<Course> courseList(long pageNum, long pageSize, long teacherId,String title) {
        Page<Course> coursePage = new Page<>(pageNum, pageSize);
        //封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();

        if(!StrUtil.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if(teacherId>0) {
           wrapper.eq("teacherId",teacherId);
        }
        Page<Course> selectPage = courseMapper.selectPage(coursePage, wrapper);
        List<Course> records = selectPage.getRecords();
        records.forEach(item ->{
            //查询讲师名称
            Long teacherId1 = item.getTeacherId();
            Teacher teacher = teacherMapper.selectById(teacherId1);
            if (teacher != null){
                item.setTeacherName(teacher.getName());
            }
            //查询分类名称
            Subject subjectOne = subjectMapper.selectById(item.getSubjectPid());
            if (subjectOne != null){
                item.setSubjectParentTitle(subjectOne.getTitle());
            }
            Subject subjectTwo = subjectMapper.selectById(item.getSubjectId());
            if (subjectTwo!=null){
                item.setSubjectTitle(subjectTwo.getTitle());
            }
        });

        return selectPage;
    }

    /**
     * 返回课程列表
     * @param title
     * @return
     */
    @Override
    public List<Course> findAll(String title) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(title)){
            courseQueryWrapper.like("title",title);
        }

        return courseMapper.selectList(courseQueryWrapper);
    }

    /**
     * 添加课程
     * @param course
     * @return
     */
    @Override
    public Integer addCourse(Course course) {
        String title = course.getTitle();
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("title",title);
        Course course1 = courseMapper.selectOne(courseQueryWrapper);
        if (course1 != null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"已经添加相应的课程");
        }

        if (title == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "课程名不能为空");
        }

        return courseMapper.insert(course);
    }
    /**
     * 修改课程
     * @param course
     * @return
     */
    @Override
    public Integer editCourse(Course course) {
        return courseMapper.updateById(course);
    }
}




