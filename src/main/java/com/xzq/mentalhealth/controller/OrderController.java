package com.xzq.mentalhealth.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.dto.ConsultationDTO;
import com.xzq.mentalhealth.model.entity.Orders;
import com.xzq.mentalhealth.service.OrdersService;
import com.xzq.mentalhealth.untils.TokenUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  订单接口
 */
@Api(tags = "订单模块")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    //根据课程id和用户id创建订单，返回订单id
    @PostMapping("/createOrder/{courseId}")
    public BaseResponse<String> addOrder(@PathVariable long courseId){
        if (courseId<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //将生成的订单号返回
        String orderNo = ordersService.saveOrders(courseId, TokenUtils.getUser());
        if (orderNo == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(orderNo);
    }

    //根据订单号查询相关的订单信息
    @GetMapping("/getOrder/{orderNo}")
    public BaseResponse<Orders> getOrder(@PathVariable String orderNo){
        if (orderNo == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Orders orders = ordersService.getOderByOrdersNo(orderNo);
        if (orders == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(orders);
    }
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param teacherName
     * @param courseTitle
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<Orders>> pageOrders(@RequestParam long pageNum,
                                                             @RequestParam long pageSize,
                                                             @RequestParam(defaultValue = "") String teacherName,
                                                             @RequestParam(defaultValue = "") String userAccount,
                                                             @RequestParam(defaultValue = "") String courseTitle) {
        if (pageNum < 0 && pageSize<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Orders> OrdersPage = ordersService.ordersList(pageNum, pageSize, teacherName,userAccount,courseTitle);
        if (OrdersPage == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(OrdersPage);
    }

    /**
     * 查询所有该用户的订单信息
     * @param userId
     * @return
     */
    @GetMapping("/findAll/{userId}")
    public BaseResponse<List<Orders>> findAllById(@PathVariable long userId){
        if(userId < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Orders> ordersList = ordersService.findAll(userId);
        if (ordersList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(ordersList);
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
        boolean removeById = ordersService.removeById(id);
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
        boolean removeBatchByIds = ordersService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }


    /**
     * 后端修改订单信息
     * @param orders
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditConsultation(@RequestBody Orders orders){
        if (orders == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = ordersService.editOrders(orders);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }


}
