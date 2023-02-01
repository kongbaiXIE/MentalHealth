package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.ConsultationMapper;
import com.xzq.mentalhealth.model.dto.ConsultationDTO;
import com.xzq.mentalhealth.model.entity.Consultation;
import com.xzq.mentalhealth.model.entity.User;
import com.xzq.mentalhealth.service.ConsultationService;
import com.xzq.mentalhealth.untils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
* @author 谢志强
* @description 针对表【Consultation(预约表)】的数据库操作Service实现
* @createDate 2023-01-30 13:56:22
*/
@Service
public class ConsultationServiceImpl extends ServiceImpl<ConsultationMapper, Consultation>
    implements ConsultationService{

    @Resource
    ConsultationMapper consultationMapper;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param teacherName
     * @param username
     * @return
     */
    @Override
    public Page<Consultation> consultationList(long pageNum, long pageSize, String teacherName, String username) {
        Page<Consultation> ConsultationPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Consultation> ConsultationQueryWrapper = new QueryWrapper<>();
        ConsultationQueryWrapper.orderByAsc("createTime");
        if (StrUtil.isNotBlank(teacherName)){
            ConsultationQueryWrapper.like("teacherName",teacherName);
        }
        if (StrUtil.isNotBlank(username)){
            ConsultationQueryWrapper.like("username",username);
        }

        return consultationMapper.selectPage(ConsultationPage, ConsultationQueryWrapper);
    }

    /**
     * 查询所有预约信息
     * @param teacherName
     * @return
     */
    @Override
    public List<Consultation> findAll(String teacherName) {
        QueryWrapper<Consultation> ConsultationQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(teacherName)){
            ConsultationQueryWrapper.eq("teacherName",teacherName);
        }
        return consultationMapper.selectList(ConsultationQueryWrapper);
    }

    /**
     * 添加预约信息
     * @param ConsultationDTO
     * @return
     */
    @Override
    public Integer addConsultation(ConsultationDTO ConsultationDTO) {
        String teacherName = ConsultationDTO.getName();
        if (teacherName == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求参数为空");
        }
        if (ConsultationDTO.getDescription() == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求参数为空");
        }
        Date ConsultationTime = ConsultationDTO.getReservationTime();
        if (ConsultationTime == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求参数为空");
        }
        //预约时间 > 当前时间
        if (new Date().after(ConsultationTime)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"预约时间要大于当前时间");
        }
        String username = ConsultationDTO.getUsername();
        User user = TokenUtils.getUser();
        //判断预约用户是否是当前登陆用户
        if (user == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (!Objects.equals(username, user.getUsername())){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        //为防止用户重复提交预约 通过用户账号和咨询师名字查询是否存在一个预约信息状态为预约中的重复预约信息
        QueryWrapper<Consultation> ConsultationQueryWrapper = new QueryWrapper<>();
        ConsultationQueryWrapper.eq("username",username);
        ConsultationQueryWrapper.eq("teacherName",teacherName);
        ConsultationQueryWrapper.eq("consultationStatus",0);
        Consultation consultation1 = consultationMapper.selectOne(ConsultationQueryWrapper);
        if (consultation1 != null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"请不要重复提交预约信息");
        }
        Consultation consultation = new Consultation();
        consultation.setTeacherName(teacherName);
        consultation.setDescription(ConsultationDTO.getDescription());
        consultation.setUsername(username);
        consultation.setReservationTime(ConsultationDTO.getReservationTime());
        consultation.setConsultAbout("空");
        return consultationMapper.insert(consultation);
    }

    /**
     * 修改预约信息
     * @param consultation
     * @return
     */
    @Override
    public Integer editConsultation(Consultation consultation) {
        return consultationMapper.updateById(consultation);
    }

    /**
     * 通过用户id查询出该用户的预约信息
     * @param userId
     * @return
     */
    @Override
    public List<Consultation> getFrontConsultation(long userId) {
        //重token获取用户id
        User user = TokenUtils.getUser();
        if (user == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        //判断是否是当前登陆用户
        if (user.getId() != userId){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        QueryWrapper<Consultation> ConsultationQueryWrapper = new QueryWrapper<>();
        ConsultationQueryWrapper.eq("username",user.getUsername());
        return consultationMapper.selectList(ConsultationQueryWrapper);
    }
}




