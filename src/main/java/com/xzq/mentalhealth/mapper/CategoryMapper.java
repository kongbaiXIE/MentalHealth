package com.xzq.mentalhealth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzq.mentalhealth.model.entity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
* @author 谢志强
* @description 针对表【category(测评类型表)】的数据库操作Mapper
* @createDate 2023-01-17 22:33:20
* @Entity generator.domain.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




