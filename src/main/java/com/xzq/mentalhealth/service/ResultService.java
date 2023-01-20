package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.model.dto.ResultDTO;
import com.xzq.mentalhealth.model.entity.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.mentalhealth.model.vo.ResultVO;

import java.util.List;

/**
* @author 谢志强
* @description 针对表【result(测评结果表)】的数据库操作Service
* @createDate 2023-01-20 19:47:11
*/
public interface ResultService extends IService<Result> {

    Page<Result> resultList(long pageNum, long pageSize, String name);

    List<Result> findAll(String name);

    Integer editResult(Result result);

    ResultVO addStuResult(ResultDTO resultDTO);
}
