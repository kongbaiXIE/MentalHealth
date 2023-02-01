package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.model.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.mentalhealth.model.entity.User;

import java.util.List;

/**
* @author 谢志强
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2023-01-28 16:07:46
*/
public interface OrdersService extends IService<Orders> {

    String saveOrders(long courseId, User user);

    Orders getOderByOrdersNo(String orderNo);

    Page<Orders> ordersList(long pageNum, long pageSize, String teacherName, String userAccount, String courseTitle);

    List<Orders> findAll(long userId);

    Integer editOrders(Orders orders);
}
