package com.xzq.mentalhealth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.model.entity.UserRole;
import com.xzq.mentalhealth.service.UserRoleService;
import com.xzq.mentalhealth.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 谢志强
* @description 针对表【user_role(用户角色关系)】的数据库操作Service实现
* @createDate 2022-12-31 20:30:17
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




