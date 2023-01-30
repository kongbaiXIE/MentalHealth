package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.TeacherMapper;
import com.xzq.mentalhealth.model.dto.ReservationDTO;
import com.xzq.mentalhealth.model.entity.Reservation;
import com.xzq.mentalhealth.model.entity.User;
import com.xzq.mentalhealth.service.ReservationService;
import com.xzq.mentalhealth.mapper.ReservationMapper;
import com.xzq.mentalhealth.untils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
* @author 谢志强
* @description 针对表【reservation(预约表)】的数据库操作Service实现
* @createDate 2023-01-30 13:56:22
*/
@Service
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation>
    implements ReservationService{

    @Resource
    ReservationMapper reservationMapper;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param teacherName
     * @param userAccount
     * @return
     */
    @Override
    public Page<Reservation> reservationList(long pageNum, long pageSize, String teacherName, String userAccount) {
        Page<Reservation> reservationPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Reservation> reservationQueryWrapper = new QueryWrapper<>();
        reservationQueryWrapper.orderByAsc("createTime");
        if (StrUtil.isNotBlank(teacherName)){
            reservationQueryWrapper.like("teacherName",teacherName);
        }
        if (StrUtil.isNotBlank(userAccount)){
            reservationQueryWrapper.like("userAccount",userAccount);
        }
        Page<Reservation> reservationPage1 = reservationMapper.selectPage(reservationPage, reservationQueryWrapper);

        return reservationPage1;
    }

    /**
     * 查询所有预约信息
     * @param teacherName
     * @return
     */
    @Override
    public List<Reservation> findAll(String teacherName) {
        QueryWrapper<Reservation> reservationQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(teacherName)){
            reservationQueryWrapper.like("teacherName",teacherName);
        }

        return reservationMapper.selectList(reservationQueryWrapper);
    }

    /**
     * 添加预约信息
     * @param reservationDTO
     * @return
     */
    @Override
    public Integer addReservation(ReservationDTO reservationDTO) {
        String teacherName = reservationDTO.getName();
        if (teacherName == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求参数为空");
        }
        if (reservationDTO.getDescription() == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求参数为空");
        }
        Date reservationTime = reservationDTO.getReservationTime();
        if (reservationTime == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求参数为空");
        }
        //预约时间 > 当前时间
        if (new Date().after(reservationTime)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"预约时间要大于当前时间");
        }
        String userAccount = reservationDTO.getUserAccount();
        User user = TokenUtils.getUser();
        //判断预约用户是否是当前登陆用户
        if (user == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (!Objects.equals(userAccount, user.getUserAccount())){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        //为防止用户重复提交预约 通过用户账号和咨询师名字查询是否存在一个预约信息状态为预约中的重复预约信息
        QueryWrapper<Reservation> reservationQueryWrapper = new QueryWrapper<>();
        reservationQueryWrapper.eq("userAccount",userAccount);
        reservationQueryWrapper.eq("teacherName",teacherName);
        reservationQueryWrapper.eq("reservationStatus",0);
        Reservation reservation1 = reservationMapper.selectOne(reservationQueryWrapper);
        if (reservation1 != null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"请不要重复提交预约信息");
        }
        Reservation reservation = new Reservation();
        reservation.setTeacherName(teacherName);
        reservation.setDescription(reservationDTO.getDescription());
        reservation.setUserAccount(userAccount);
        reservation.setReservationTime(reservationDTO.getReservationTime());
        return reservationMapper.insert(reservation);
    }

    /**
     * 修改预约信息
     * @param reservation
     * @return
     */
    @Override
    public Integer editReservation(Reservation reservation) {
        return reservationMapper.updateById(reservation);
    }
}




