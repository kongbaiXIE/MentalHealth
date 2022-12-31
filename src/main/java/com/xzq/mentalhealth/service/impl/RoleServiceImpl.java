package com.xzq.mentalhealth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.model.entity.Role;
import com.xzq.mentalhealth.service.RoleService;
import com.xzq.mentalhealth.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 谢志强
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2022-12-31 20:29:39
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




