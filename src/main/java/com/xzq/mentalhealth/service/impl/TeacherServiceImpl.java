package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.CourseMapper;
import com.xzq.mentalhealth.model.entity.Course;
import com.xzq.mentalhealth.model.entity.Teacher;
import com.xzq.mentalhealth.model.vo.TeacherFrontVO;
import com.xzq.mentalhealth.service.TeacherService;
import com.xzq.mentalhealth.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
* @author 谢志强
* @description 针对表【teacher(讲师表)】的数据库操作Service实现
* @createDate 2023-01-22 15:59:38
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

    @Resource
    TeacherMapper teacherMapper;
    @Resource
    CourseMapper courseMapper;
    /**
     * 分页条件查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @param level
     * @return
     */
    @Override
    public Page<Teacher> teacherList(long pageNum, long pageSize, String name, Integer level) {
        Page<Teacher> teacherPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByAsc("createTime");
        if (!"".equals(name)) {
            teacherQueryWrapper.like("name", name);
        }
        if(level!=null){
            teacherQueryWrapper.eq("level",level);
        }
        return teacherMapper.selectPage(teacherPage, teacherQueryWrapper);
    }

    /**
     * 查询讲师列表
     * @param name
     * @return
     */
    @Override
    public List<Teacher> findAll(String name) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)){
            teacherQueryWrapper.like("name",name);
        }
        return teacherMapper.selectList(teacherQueryWrapper);
    }

    /**
     * 查询讲师列表
     * @param name
     * @return
     */
    @Override
    public List<Teacher> findAllByLimit(String name) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)){
            teacherQueryWrapper.like("name",name);
        }
        //使用last方法拼接sql语句
        teacherQueryWrapper.last("limit 8");
        return teacherMapper.selectList(teacherQueryWrapper);
    }

    /**
     * 添加讲师
     * @param teacher
     * @return
     */
    @Override
    public Integer addTeacher(Teacher teacher) {
        String name = teacher.getName();
        if (name == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return teacherMapper.insert(teacher);
    }

    /**
     * 修改讲师
     * @param teacher
     * @return
     */
    @Override
    public Integer editTeacher(Teacher teacher) {
        return teacherMapper.updateById(teacher);
    }

    /**
     * 通过咨询师id返回咨询师相关信息和该咨询师的所有课程
     * @param teacherId
     * @return
     */
    @Override
    public TeacherFrontVO getTeacherFrontInfo(long teacherId) {

        Teacher teacher = teacherMapper.selectById(teacherId);
        if (teacher == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"没有查询到该咨询师");
        }
        //通过咨询师id查询出对应的课程
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacherId",teacher.getId());
        List<Course> courses = courseMapper.selectList(courseQueryWrapper);
        TeacherFrontVO teacherFrontVO = new TeacherFrontVO();
        teacherFrontVO.setTeacher(teacher);
        teacherFrontVO.setCourseList(courses);

        return teacherFrontVO;
    }
}




