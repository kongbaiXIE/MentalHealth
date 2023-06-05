package com.xzq.mentalhealth.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.dto.ConsultationDTO;
import com.xzq.mentalhealth.model.entity.Consultation;
import com.xzq.mentalhealth.service.ConsultationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = "咨询模块")
@RestController
@RequestMapping("/consultation")
public class ConsultationController {


    @Autowired
    private ConsultationService consultationService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param teacherName
     * @param username
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<Consultation>> pageConsultation(@RequestParam long pageNum,
                                                            @RequestParam long pageSize,
                                                            @RequestParam(defaultValue = "") String teacherName,
                                                            @RequestParam(defaultValue = "") String username) {
        if (pageNum < 0 && pageSize<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Consultation> ConsultationPage = consultationService.consultationList(pageNum, pageSize, teacherName,username);
        if (ConsultationPage == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(ConsultationPage);
    }

    /**
     * 查询所有预约信息
     * @param teacherName
     * @return
     */
    @GetMapping("/findAll")
    public BaseResponse<List<Consultation>> findAll(@RequestParam(defaultValue = " ") String teacherName){
        if(teacherName == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Consultation> consultationList = consultationService.findAll(teacherName);
        if (consultationList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(consultationList);
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
        boolean removeById = consultationService.removeById(id);
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
        boolean removeBatchByIds = consultationService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 添加预约
     * @param ConsultationDTO
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<Integer> addConsultation(@RequestBody ConsultationDTO ConsultationDTO){
        if (ConsultationDTO == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = consultationService.addConsultation(ConsultationDTO);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 后端修改预约信息
     * @param consultation
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditConsultation(@RequestBody Consultation consultation){
        if (consultation == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = consultationService.editConsultation(consultation);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 通过用户id查询出该用户的预约信息
     * @param userId
     * @return
     */
    @GetMapping("/getFrontConsultation/{userId}")
    public BaseResponse<List<Consultation>> getFrontByUserId(@PathVariable long userId){
        if (userId<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Consultation> consultationList = consultationService.getFrontConsultation(userId);
        if (consultationList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(consultationList);
    }

    /**
     * 修改咨询开始时间
     * @param consultationId
     * @return
     */
    @GetMapping("/updateFrontConsultation/{consultationId}")
    public BaseResponse<Boolean> updateByConsultation(@PathVariable long consultationId){
        if (consultationId < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Consultation consultation = consultationService.getById(consultationId);
        consultation.setStartTime(new Date());
        boolean b = consultationService.updateById(consultation);

        return ResultUtil.success(b);
    }
    /**
     * 修改咨询结束时间 和 状态
     * @param consultationId
     * @return
     */
    @GetMapping("/updateFrontConsultationEnd/{consultationId}")
    public BaseResponse<Boolean> updateByConsultationEnd(@PathVariable long consultationId){
        if (consultationId < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Consultation consultation = consultationService.getById(consultationId);
        consultation.setEndTime(new Date());
        consultation.setConsultationStatus(3);
        boolean b = consultationService.updateById(consultation);

        return ResultUtil.success(b);
    }


}
