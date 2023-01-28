package com.xzq.mentalhealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.mapper.OrdersMapper;
import com.xzq.mentalhealth.model.entity.Course;
import com.xzq.mentalhealth.model.entity.Orders;
import com.xzq.mentalhealth.model.entity.User;
import com.xzq.mentalhealth.service.CourseService;
import com.xzq.mentalhealth.service.OrdersService;
import com.xzq.mentalhealth.untils.OrderNoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 谢志强
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2023-01-28 16:13:18
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService {

    @Resource
    com.xzq.mentalhealth.mapper.OrdersMapper ordersMapper;

    @Resource
    CourseService courseService;

    /**
     * 根据课程id和用户id创建订单，返回订单号
     * @param courseId
     * @param user
     * @return
     */
    @Override
    public String saveOrders(long courseId, User user) {
        //通过课程id获取相关的课程信息
        Course coursePublish = courseService.getCoursePublish(courseId);
        //用户数据通过token获取得到
        Long userId = user.getId();
        String userAccount = user.getUserAccount();
        com.xzq.mentalhealth.model.entity.Orders orders = new com.xzq.mentalhealth.model.entity.Orders();
        orders.setOrderNo(OrderNoUtil.getOrderNo());
        orders.setCourseId(courseId);
        orders.setCourseTitle(coursePublish.getTitle());
        orders.setCourseCover(coursePublish.getCover());
        orders.setTeacherName(coursePublish.getTeacherName());
        orders.setTotalFee(coursePublish.getPrice());
        orders.setUserId(userId);
        orders.setUserAccount(userAccount);
        orders.setStatus(0);//订单状态（0：未支付 1：已支付）
        orders.setPayType(2);//支付类型 ，支付宝2
        ordersMapper.insert(orders);

        return orders.getOrderNo();
    }

    /**
     * 根据订单号查询相关订单信息
     * @param orderNo
     * @return
     */
    @Override
    public Orders getOderByOrdersNo(String orderNo) {
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("orderNo",orderNo);

        return ordersMapper.selectOne(ordersQueryWrapper);
    }
}




