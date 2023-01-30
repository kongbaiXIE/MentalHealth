package com.xzq.mentalhealth.mapper;

import com.xzq.mentalhealth.model.entity.Reservation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【reservation(预约表)】的数据库操作Mapper
* @createDate 2023-01-30 13:56:22
* @Entity com.xzq.mentalhealth.model.entity.Reservation
*/
@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {

}




