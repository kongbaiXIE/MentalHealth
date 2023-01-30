package com.xzq.mentalhealth.controller;

import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Subject;
import com.xzq.mentalhealth.service.SubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程分类接口
 */
@Api(tags = "课程分类模块")
@RestController
@RequestMapping("/subject")
public class SubjectController {


    @Autowired
    private SubjectService subjectService;

    /**
     * 查看课程分类列表
     * @param name
     * @return
     */
    @GetMapping("/findAll")
    public BaseResponse<List<Subject>> findAll(@RequestParam(defaultValue = " ") String name){
        if(name == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Subject> subjectList = subjectService.findAll(name);
        if (subjectList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(subjectList);
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
        boolean removeById = subjectService.removeById(id);
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
        boolean removeBatchByIds = subjectService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 添加课程分类
     * @param subject
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<Integer> addSubject(@RequestBody Subject subject){
        if (subject == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = subjectService.addSubject(subject);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 修改课程分类
     * @param subject
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> editSubject(@RequestBody Subject subject){
        if (subject == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = subjectService.editSubject(subject);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }


}
