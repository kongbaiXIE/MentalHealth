package com.xzq.mentalhealth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.User;
import com.xzq.mentalhealth.model.vo.RoleTeacherVO;
import com.xzq.mentalhealth.model.vo.TeacherFrontVO;
import com.xzq.mentalhealth.service.UserService;
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
    private UserService userService;



    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<User>> pageTeacher(@RequestParam long pageNum,
                                                @RequestParam long pageSize,
                                                @RequestParam(defaultValue = "") String name) {
        if (pageNum < 0 && pageSize<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<User> TeacherList = userService.teacherList(pageNum, pageSize, name);
        if (TeacherList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(TeacherList);
    }

    /**
     * 返回给前台的课程数据
     * @param title
     * @return
     */
    @GetMapping("/findAllByLimit")
    public BaseResponse<List<RoleTeacherVO>> findAllByLimit(@RequestParam(defaultValue = " ") String title){
        if(title == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<RoleTeacherVO> allByLimit = userService.findAllByLimit(title);
        if (allByLimit == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(allByLimit);
    }


    /**
     * 通过咨询师id返回咨询师相关信息和该咨询师的所有课程
     * @param teacherId
     * @return
     */
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public BaseResponse<TeacherFrontVO> getTeacherFrontInfo(@PathVariable long teacherId){
        if(teacherId < 0){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        TeacherFrontVO teacherFrontVO =  userService.getTeacherFrontInfo(teacherId);
        if (teacherFrontVO == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(teacherFrontVO);
    }
    ///**
    // * 分页查询
    // * @param pageNum
    // * @param pageSize
    // * @param name
    // * @return
    // */
    //@GetMapping("/page")
    //public BaseResponse<Page<Teacher>> pageTeacher(@RequestParam long pageNum,
    //                                               @RequestParam long pageSize,
    //                                               @RequestParam(defaultValue = "") String name,
    //                                               @RequestParam(defaultValue = "") Integer level) {
    //    if (pageNum < 0 && pageSize<0){
    //        throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //    }
    //    Page<Teacher> TeacherList = teacherService.teacherList(pageNum, pageSize, name,level);
    //    if (TeacherList == null){
    //        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    //    }
    //
    //    return ResultUtil.success(TeacherList);
    //}

    ///**
    // * 查询讲师表所有数据
    // * @param name
    // * @return
    // */
    //@GetMapping("/findAll")
    //public BaseResponse<List<Teacher>> findAll(@RequestParam(defaultValue = " ") String name){
    //    if(name == null){
    //        throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //    }
    //    List<Teacher> teacherList = teacherService.findAll(name);
    //    if (teacherList == null){
    //        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    //    }
    //    return ResultUtil.success(teacherList);
    //}
    ///**
    // * 返回给前台的课程数据
    // * @param title
    // * @return
    // */
    //@GetMapping("/findAllByLimit")
    //public BaseResponse<List<Teacher>> findAllByLimit(@RequestParam(defaultValue = " ") String title){
    //    if(title == null){
    //        throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //    }
    //    List<Teacher> allByLimit = teacherService.findAllByLimit(title);
    //    if (allByLimit == null){
    //        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    //    }
    //    return ResultUtil.success(allByLimit);
    //}
    ///**
    // * 删除数据
    // * @param id
    // * @return
    // */
    //@PostMapping("/del/{id}")
    //public BaseResponse<Boolean> deleteById(@PathVariable long id){
    //    if (id <=0 ){
    //        throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //    }
    //    boolean removeById = teacherService.removeById(id);
    //    if (!removeById){
    //        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
    //    }
    //    return ResultUtil.success(true);
    //}

    ///**
    // * 批量删除数据
    // * @param ids
    // * @return
    // */
    //@PostMapping("/del/batch")
    //public BaseResponse<Boolean>  deleteBatch(@RequestBody List<Long> ids){
    //    if (ids == null){
    //        throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //    }
    //    boolean removeBatchByIds = teacherService.removeBatchByIds(ids);
    //    if (!removeBatchByIds){
    //        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
    //    }
    //    return ResultUtil.success(true);
    //}
    //
    ///**
    // * 添加题目
    // * @param Teacher
    // * @return
    // */
    //@PostMapping("/save")
    //public BaseResponse<Integer> addTeacher(@RequestBody Teacher Teacher){
    //    if (Teacher == null){
    //        throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //    }
    //    Integer integer = teacherService.addTeacher(Teacher);
    //    if (integer < 0){
    //        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
    //    }
    //    return ResultUtil.success(integer);
    //}
    //
    ///**
    // * 后端修改咨询师信息
    // * @param Teacher
    // * @return
    // */
    //@PostMapping("/edit")
    //public BaseResponse<Integer> EditTeacher(@RequestBody Teacher Teacher){
    //    if (Teacher == null){
    //        throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //    }
    //    Integer integer = teacherService.editTeacher(Teacher);
    //    if (integer < 0){
    //        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
    //    }
    //    return ResultUtil.success(integer);
    //}

    ///**
    // * 通过咨询师id返回咨询师相关信息和该咨询师的所有课程
    // * @param teacherId
    // * @return
    // */
    //@GetMapping("/getTeacherFrontInfo/{teacherId}")
    //public BaseResponse<TeacherFrontVO> getTeacherFrontInfo(@PathVariable long teacherId){
    //    if(teacherId < 0){
    //        throw new BusinessException(ErrorCode.NULL_ERROR);
    //    }
    //    TeacherFrontVO teacherFrontVO =  teacherService.getTeacherFrontInfo(teacherId);
    //    if (teacherFrontVO == null){
    //        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    //    }
    //
    //    return ResultUtil.success(teacherFrontVO);
    //}

    ///**
    // * 通过咨询师id返回咨询师相关信息和该咨询师的所有课程
    // * @param teacherId
    // * @return
    // */
    //@GetMapping("/getTeacherFrontInfo/{teacherId}")
    //public BaseResponse<TeacherFrontVO> getTeacherFrontInfo(@PathVariable long teacherId){
    //    if(teacherId < 0){
    //        throw new BusinessException(ErrorCode.NULL_ERROR);
    //    }
    //    TeacherFrontVO teacherFrontVO =  teacherService.getTeacherFrontInfo(teacherId);
    //    if (teacherFrontVO == null){
    //        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    //    }
    //
    //    return ResultUtil.success(teacherFrontVO);
    //}
}
