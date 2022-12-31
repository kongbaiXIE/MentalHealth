package com.xzq.mentalhealth.mapper;

import com.xzq.mentalhealth.model.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【role(角色表)】的数据库操作Mapper
* @createDate 2022-12-31 20:29:39
* @Entity com.xzq.mentalhealth.model.entity.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}




