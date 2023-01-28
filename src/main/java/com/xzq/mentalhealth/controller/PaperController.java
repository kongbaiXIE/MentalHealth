package com.xzq.mentalhealth.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.config.AuthAccess;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.dto.HandPaperDTO;
import com.xzq.mentalhealth.model.dto.PaperDTO;
import com.xzq.mentalhealth.model.entity.Paper;
import com.xzq.mentalhealth.model.entity.Question;
import com.xzq.mentalhealth.service.PaperService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测评问卷接口
 */
@Api(tags = "测评类型模块")
@RestController
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<Paper>> pagePaper(@RequestParam long pageNum,
                                                             @RequestParam long pageSize,
                                                             @RequestParam(defaultValue = "") String name) {
        if (pageNum < 0 && pageSize<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Paper> paperList = paperService.PaperList(pageNum, pageSize, name);
        if (paperList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(paperList);
    }

    /**
     * 创建问卷
     * @return
     */
    @PostMapping("/takePaper")
    public BaseResponse<Boolean> takePaper(@RequestBody PaperDTO paperDTO){
        if (paperDTO == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Boolean aBoolean = paperService.takePaper(paperDTO);
        if (!aBoolean){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"组卷失败");
        }
        return ResultUtil.success(aBoolean);
    }

    /**
     * 手动创建问卷
     * @param handPaperDTO
     * @return
     */
    @PostMapping("/handPaper")
    public BaseResponse<Boolean> handPaper(@RequestBody HandPaperDTO handPaperDTO){
        if (CollUtil.isEmpty(handPaperDTO.getHandQuestionIds())){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Boolean aBoolean = paperService.handPaper(handPaperDTO);
        if (!aBoolean){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"组卷失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 查看问卷
     * @param paperId
     * @return
     */
    @GetMapping("/view/{paperId}")
    public BaseResponse<List<Question>> viewPaper(@PathVariable Long paperId){
        if (paperId<0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<Question> questions = paperService.viewPaper(paperId);
        if (questions == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"该问卷还没组卷");
        }
        return ResultUtil.success(questions);
    }
    /**
     * 返回给学生的问卷
     * @param paperId
     * @return
     */
    @GetMapping("/safe/view/{paperId}")
    public BaseResponse<List<Question>> safeViewPaper(@PathVariable Long paperId){
        if (paperId<0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<Question> questions = paperService.safeViewPaper(paperId);
        if (questions == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"该问卷还没组卷");
        }
        return ResultUtil.success(questions);
    }
    /**
     * 查看测评问卷
     * @param name
     * @return
     */
    @AuthAccess
    @GetMapping("/findAll")
    public BaseResponse<List<Paper>> findAll(@RequestParam(defaultValue = " ") String name){
        if(name == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Paper> paperList = paperService.findAll(name);
        if (paperList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(paperList);
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
        boolean removeById = paperService.removeById(id);
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
        boolean removeBatchByIds = paperService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 添加测评类型
     * @param Paper
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<Integer> addQuestionPaper(@RequestBody Paper Paper){
        if (Paper == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = paperService.addPaper(Paper);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 修改测评类型
     * @param Paper
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditQuestionPaper(@RequestBody Paper Paper){
        if (Paper == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = paperService.editPaper(Paper);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }

}
