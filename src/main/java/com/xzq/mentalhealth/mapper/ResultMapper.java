package com.xzq.mentalhealth.mapper;

import com.xzq.mentalhealth.model.entity.Result;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【result(测评结果表)】的数据库操作Mapper
* @createDate 2023-01-20 19:47:11
* @Entity com.xzq.mentalhealth.model.entity.Result
*/
@Mapper
public interface ResultMapper extends BaseMapper<Result> {

}




