package com.xzq.mentalhealth.service;

import com.xzq.mentalhealth.model.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.mentalhealth.model.entity.User;

/**
* @author 谢志强
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2023-01-28 16:07:46
*/
public interface OrdersService extends IService<Orders> {

    String saveOrders(long courseId, User user);

    Orders getOderByOrdersNo(String orderNo);
}
