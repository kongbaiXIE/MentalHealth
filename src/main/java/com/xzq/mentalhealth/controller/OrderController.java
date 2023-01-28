package com.xzq.mentalhealth.controller;


import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Orders;
import com.xzq.mentalhealth.service.OrdersService;
import com.xzq.mentalhealth.untils.TokenUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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



}
