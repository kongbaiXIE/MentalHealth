package com.xzq.mentalhealth.mapper;

import com.xzq.mentalhealth.model.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【user_role(用户角色关系)】的数据库操作Mapper
* @createDate 2022-12-31 20:30:17
* @Entity com.xzq.mentalhealth.model.entity.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




