package com.xzq.mentalhealth.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.dto.ReservationDTO;
import com.xzq.mentalhealth.model.entity.Reservation;
import com.xzq.mentalhealth.service.ReservationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "预约模块")
@RestController
@RequestMapping("/reservation")
public class ReservationController {


    @Autowired
    private ReservationService reservationService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param teacherName
     * @param userAccount
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<Reservation>> pageReservation(@RequestParam long pageNum,
                                                   @RequestParam long pageSize,
                                                   @RequestParam(defaultValue = "") String teacherName,
                                                   @RequestParam(defaultValue = "") String userAccount) {
        if (pageNum < 0 && pageSize<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Reservation> reservationPage = reservationService.reservationList(pageNum, pageSize, teacherName,userAccount);
        if (reservationPage == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(reservationPage);
    }

    /**
     * 查询所有预约信息
     * @param teacherName
     * @return
     */
    @GetMapping("/findAll")
    public BaseResponse<List<Reservation>> findAll(@RequestParam(defaultValue = " ") String teacherName){
        if(teacherName == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Reservation> reservationList = reservationService.findAll(teacherName);
        if (reservationList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(reservationList);
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
        boolean removeById = reservationService.removeById(id);
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
        boolean removeBatchByIds = reservationService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 添加预约
     * @param reservationDTO
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<Integer> addReservation(@RequestBody ReservationDTO reservationDTO){
        if (reservationDTO == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = reservationService.addReservation(reservationDTO);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 后端修改预约信息
     * @param reservation
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditTeacher(@RequestBody Reservation reservation){
        if (reservation == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = reservationService.editReservation(reservation);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }


}
