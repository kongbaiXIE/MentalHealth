package com.xzq.mentalhealth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Teacher;
import com.xzq.mentalhealth.service.TeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程讲师接口
 */
@Api(tags = "讲师模块")
@RestController
@RequestMapping("/teacher")
public class TeacherController {


    @Autowired
    private TeacherService teacherService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<Teacher>> pageTeacher(@RequestParam long pageNum,
                                                   @RequestParam long pageSize,
                                                   @RequestParam(defaultValue = "") String name,
                                                   @RequestParam(defaultValue = "") Integer level) {
        if (pageNum < 0 && pageSize<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Teacher> TeacherList = teacherService.teacherList(pageNum, pageSize, name,level);
        if (TeacherList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(TeacherList);
    }

    /**
     * 查询讲师表所有数据
     * @param name
     * @return
     */
    @GetMapping("/findAll")
    public BaseResponse<List<Teacher>> findAll(@RequestParam(defaultValue = " ") String name){
        if(name == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Teacher> teacherList = teacherService.findAll(name);
        if (teacherList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(teacherList);
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
        boolean removeById = teacherService.removeById(id);
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
        boolean removeBatchByIds = teacherService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 添加题目
     * @param Teacher
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<Integer> addTeacher(@RequestBody Teacher Teacher){
        if (Teacher == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = teacherService.addTeacher(Teacher);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 修改菜单
     * @param Teacher
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditTeacher(@RequestBody Teacher Teacher){
        if (Teacher == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = teacherService.editTeacher(Teacher);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }


}
