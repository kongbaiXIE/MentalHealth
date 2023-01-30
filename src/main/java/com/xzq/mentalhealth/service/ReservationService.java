package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.model.dto.ReservationDTO;
import com.xzq.mentalhealth.model.entity.Reservation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 谢志强
* @description 针对表【reservation(预约表)】的数据库操作Service
* @createDate 2023-01-30 13:56:22
*/
public interface ReservationService extends IService<Reservation> {

    Page<Reservation> reservationList(long pageNum, long pageSize, String teacherName, String userAccount);

    List<Reservation> findAll(String teacherName);

    Integer addReservation(ReservationDTO reservationDTO);

    Integer editReservation(Reservation reservation);
}
