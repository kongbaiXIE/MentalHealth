package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.*;
import com.xzq.mentalhealth.model.entity.*;
import com.xzq.mentalhealth.model.vo.CourseFrontVO;
import com.xzq.mentalhealth.service.CourseService;
import com.xzq.mentalhealth.untils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;


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
    UserMapper userMapper;
    @Resource
    SubjectMapper subjectMapper;
    @Resource
    ChapterMapper chapterMapper;
    @Resource
    VideoMapper videoMapper;
    @Resource
    OrdersMapper ordersMapper;
    /**
     * 后端分页查询
     * @param pageNum
     * @param pageSize
     * @param userIdByTeacher
     * @param title
     * @return
     */
    @Override
    public Page<Course> courseList(long pageNum, long pageSize, long userIdByTeacher,String title) {
        Page<Course> coursePage = new Page<>(pageNum, pageSize);
        //封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("createTime");
        if(!StrUtil.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if (userIdByTeacher>0){
            wrapper.eq("userId",userIdByTeacher);
        }
        //判断当前用户的角色信息，如果是管理员则可以查询所有课程信息，如果是咨询师则只能查看自己发布的课程
        User currentUser = TokenUtils.getUser();
        if (currentUser == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String role = currentUser.getRole();
        if (Objects.equals(role, "ROLE_TEACHER")){
            wrapper.eq("userId",currentUser.getId());
        }
        Page<Course> selectPage = courseMapper.selectPage(coursePage, wrapper);
        List<Course> records = selectPage.getRecords();
        records.forEach(item ->{
            //查询讲师名称
            Long userId1 = item.getUserId();
            User user = userMapper.selectById(userId1);
            if (user != null){
                item.setUsername(user.getUsername());
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
     * 返回给前台的课程数据
     * @param title
     * @return
     */
    @Override
    public List<Course> findAllByLimit(String title) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(title)){
            courseQueryWrapper.like("title",title);
        }
        //前台课程需要展示课程状态为发布状态
        courseQueryWrapper.eq("status","1");
        courseQueryWrapper.last("limit 8");
        return courseMapper.selectList(courseQueryWrapper);
    }

    /**
     * 添加课程
     * @param course
     * @return
     */
    @Override
    public Long addCourse(Course course) {
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
        if (courseMapper.insert(course)<0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        //将添加的课程id返回
        Course courseById = courseMapper.selectOne(courseQueryWrapper);
        return courseById.getId();
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

    /**
     * 查询课程相关信息
     * @param courseId
     * @return
     */
    @Override
    public Course getCoursePublish(long courseId) {
        Course course = courseMapper.selectById(courseId);
        Long userId = course.getUserId();
        Long subjectId = course.getSubjectId();
        Long subjectPid = course.getSubjectPid();
        User user = userMapper.selectById(userId);
        if (user!=null){
            course.setUsername(user.getUsername());
        }
        //查询分类名称
        Subject subjectOne = subjectMapper.selectById(subjectPid);
        if (subjectOne!=null){
            course.setSubjectParentTitle(subjectOne.getTitle());
        }
        Subject subjectTwo = subjectMapper.selectById(subjectId);
        if (subjectTwo!=null){
            course.setSubjectTitle(subjectTwo.getTitle());
        }

        return course;
    }

    /**
     * 课程关联了章节和小节表所以删除需要全部删除
     * @param id
     * @return
     */
    @Override
    public boolean removeByAlls(long id) {
        //根据课程id删除对应的章节数据
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("courseId",id);
        chapterMapper.delete(chapterQueryWrapper);
        //根据课程id删除对应小节数据
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("courseId",id);
        videoMapper.delete(videoQueryWrapper);
        int i = courseMapper.deleteById(id);
        if (i<0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return true;
    }

    /**
     * 根据课程id查询出前端需要的课程数据
     * @param courseId
     * @return
     */
    @Override
    public CourseFrontVO getFrontCourseInfo(long courseId) {
        Course course = courseMapper.selectById(courseId);
        Long userId = course.getUserId();
        CourseFrontVO courseFrontVO = new CourseFrontVO();
        User user1 = userMapper.selectById(userId);
        if (user1!=null){
            courseFrontVO.setTeacherName(user1.getUsername());
            courseFrontVO.setAvatar(user1.getAvatarUrl());
        }
        //判断用户是否已经购买该课程
        User user = TokenUtils.getUser();
        if (user == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        //通过课程id 和 用户id 查询
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("userId",user.getId());
        ordersQueryWrapper.eq("courseId",course.getId());
        //查询该用户对应该课程id
        Orders orders = ordersMapper.selectOne(ordersQueryWrapper);
        if (orders != null){
            Integer status = orders.getStatus();
            courseFrontVO.setBuyCourse(status == 1);
        }
        //获取用户章节和小节标题
        Subject subjectOne = subjectMapper.selectById(course.getSubjectPid());
        if (subjectOne!=null){
            course.setSubjectParentTitle(subjectOne.getTitle());
        }
        Subject subjectTwo = subjectMapper.selectById(course.getSubjectId());
        if (subjectTwo!=null){
            course.setSubjectTitle(subjectTwo.getTitle());
        }
        BeanUtils.copyProperties(course,courseFrontVO);

        return courseFrontVO;
    }

    /**
     * 分页前端查询
     * @param pageNum
     * @param pageSize
     * @param title
     * @param subjectId
     * @param subjectPid
     * @return
     */
    @Override
    public Page<Course> courseFrontList(long pageNum, long pageSize, String title,long subjectId,long subjectPid) {

        Page<Course> coursePage = new Page<>(pageNum, pageSize);
        //封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if(!StrUtil.isEmpty(title)) {
            wrapper.like("title",title);
        }
        //前台课程需要展示课程状态为发布状态
        wrapper.eq("status","1");
        if(subjectId > 0){
            wrapper.eq("subjectId",subjectId);
        }
        if(subjectPid > 0){
            wrapper.eq("subjectPid",subjectPid);
        }
        Page<Course> selectPage = courseMapper.selectPage(coursePage, wrapper);
        return selectPage;
    }
}




