package com.xzq.mentalhealth.mapper;

import com.xzq.mentalhealth.model.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2023-01-28 16:07:46
* @Entity com.xzq.mentalhealth.model.entity.Orders
*/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




