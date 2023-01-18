package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.mentalhealth.model.dto.PaperDTO;
import com.xzq.mentalhealth.model.entity.Paper;
import com.xzq.mentalhealth.model.entity.Question;


import java.util.List;


/**
* @author 谢志强
* @description 针对表【paper(测评问卷表)】的数据库操作Service
* @createDate 2023-01-18 11:55:41
*/
public interface PaperService extends IService<Paper> {
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    Page<Paper> PaperList(long pageNum, long pageSize, String name);

    List<Paper> findAll(String name);

    Integer addPaper(Paper paper);

    Integer editPaper(Paper paper);
    //创建问卷
    Boolean takePaper(PaperDTO paperDTO);
    //查看问卷
    List<Question> viewPaper(Long paperId);
}
