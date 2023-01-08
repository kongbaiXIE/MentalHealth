package com.xzq.mentalhealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.RoleMapper;
import com.xzq.mentalhealth.model.entity.Role;
import com.xzq.mentalhealth.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* @author 谢志强
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2022-12-31 20:29:39
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{
    @Resource
    RoleMapper roleMapper;
    @Override
    public Page<Role> roleList(long pageNum, long pageSize, String name) {
        Page<Role> userPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Role> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.orderByAsc("id");
        if (!"".equals(name)) {
            userQueryWrapper.like("name", name);
        }
        return roleMapper.selectPage(userPage, userQueryWrapper);
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @Override
    public Integer addRole(Role role) {
        if (role.getName()==null && role.getComment() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"角色名称和描述不能为空");
        }
        return roleMapper.insert(role);
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @Override
    public Integer editRole(Role role) {
        //通过id查询出用户
        Long roleId = role.getId();
        //拿到数据库中的用户数据
        Role oldRole = roleMapper.selectById(roleId);
        if (Objects.equals(oldRole.getName(), role.getName())){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"角色名称未发生改变");
        }
        return roleMapper.updateById(role);
    }
}




