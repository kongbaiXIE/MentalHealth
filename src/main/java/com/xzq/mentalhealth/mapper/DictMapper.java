package com.xzq.mentalhealth.mapper;

import com.xzq.mentalhealth.model.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【dict(存放图标)】的数据库操作Mapper
* @createDate 2023-01-14 22:33:17
* @Entity com.xzq.mentalhealth.model.entity.Dict
*/
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

}




