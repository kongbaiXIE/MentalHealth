package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.model.dto.ConsultationDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.mentalhealth.model.entity.Consultation;

import java.util.List;

/**
* @author 谢志强
* @description 针对表【consultation(咨询记录表)】的数据库操作Service
* @createDate 2023-02-01 10:52:18
*/
public interface ConsultationService extends IService<Consultation> {

    Page<Consultation> consultationList(long pageNum, long pageSize, String teacherName, String username);

    List<Consultation> findAll(String teacherName);

    Integer addConsultation(ConsultationDTO consultationDTO);

    Integer editConsultation(Consultation consultation);

    List<Consultation> getFrontConsultation(long userId);
}
