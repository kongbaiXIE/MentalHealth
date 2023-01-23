package com.xzq.mentalhealth.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Course;
import com.xzq.mentalhealth.service.CourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程接口
 */
@Api(tags = "课程模块")
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;


    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param teacherId
     * @param title
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<Course>> pageQuestion(@RequestParam long pageNum,
                                                   @RequestParam long pageSize,
                                                   @RequestParam(defaultValue = "-1")long teacherId,
                                                   @RequestParam(defaultValue = "") String title) {
        if (pageNum < 0 && pageSize<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        Page<Course> courseList = courseService.courseList(pageNum, pageSize, teacherId,title);
        if (courseList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(courseList);
    }

    /**
     * 查看测评题目列表
     * @param title
     * @return
     */
    @GetMapping("/findAll")
    public BaseResponse<List<Course>> findAll(@RequestParam(defaultValue = " ") String title){
        if(title == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Course> courseList = courseService.findAll(title);
        if (courseList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(courseList);
    }
    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/del/{id}")
    public BaseResponse<Boolean> deleteById(@PathVariable long id){
        if (id <=0 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean removeById = courseService.removeById(id);
        if (!removeById){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 批量删除数据
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public BaseResponse<Boolean>  deleteBatch(@RequestBody List<Long> ids){
        if (ids == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean removeBatchByIds = courseService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 添加题目
     * @param course
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<Integer> addQuestion(@RequestBody Course course){
        if (course == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = courseService.addCourse(course);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 修改菜单
     * @param course
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditQuestion(@RequestBody Course course){
        if (course == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = courseService.editCourse(course);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }


}
