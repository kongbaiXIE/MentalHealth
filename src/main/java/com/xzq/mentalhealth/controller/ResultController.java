package com.xzq.mentalhealth.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.config.AuthAccess;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.dto.ResultDTO;
import com.xzq.mentalhealth.model.entity.Result;
import com.xzq.mentalhealth.model.vo.ResultVO;
import com.xzq.mentalhealth.service.ResultService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测评问卷接口
 */
@Api(tags = "测评结果模块")
@RestController
@RequestMapping("/result")
public class ResultController {



    @Autowired
    private ResultService resultService;

    /**
     * 通过比对学生提交的问卷得出结果
     * @param resultDTO
     * @return
     */
    @PostMapping("/addStuQuestions")
    public BaseResponse<ResultVO> addStuResult(@RequestBody ResultDTO resultDTO){
        if (CollUtil.isEmpty(resultDTO.getAddQuestion())){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        ResultVO resultVO = resultService.addStuResult(resultDTO);
        if (resultVO == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"提交的问卷信息有误");
        }
        return ResultUtil.success(resultVO);

    }

    /**
     * 分页查询结果
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<Result>> pageResult(@RequestParam long pageNum,
                                               @RequestParam long pageSize,
                                               @RequestParam(defaultValue = "") String name) {
        if (pageNum < 0 && pageSize<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Result> ResultList = resultService.resultList(pageNum, pageSize, name);
        if (ResultList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(ResultList);
    }
    /**
     * 查看测评结果
     * @param name
     * @return
     */
    @AuthAccess
    @GetMapping("/findAll")
    public BaseResponse<List<Result>> findAll(@RequestParam(defaultValue = " ") String name){
        if(name == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Result> ResultList = resultService.findAll(name);
        if (ResultList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(ResultList);
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
        boolean removeById = resultService.removeById(id);
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
        boolean removeBatchByIds = resultService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 修改测评结果
     * @param Result
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditResult(@RequestBody Result Result){
        if (Result == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = resultService.editResult(Result);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }


}
