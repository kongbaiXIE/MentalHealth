package com.xzq.mentalhealth.mapper;

import com.xzq.mentalhealth.model.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【menu(菜单表)】的数据库操作Mapper
* @createDate 2023-01-08 22:00:53
* @Entity com.xzq.mentalhealth.model.entity.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}




