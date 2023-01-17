package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.RoleMapper;
import com.xzq.mentalhealth.mapper.RoleMenuMapper;
import com.xzq.mentalhealth.model.entity.Menu;
import com.xzq.mentalhealth.model.entity.Role;
import com.xzq.mentalhealth.model.entity.RoleMenu;
import com.xzq.mentalhealth.service.MenuService;
import com.xzq.mentalhealth.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Resource
    RoleMenuMapper roleMenuMapper;
    @Resource
    MenuService menuService;


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
    @Transactional
    @Override
    public void addRoleMenu(Long roleId, List<Long> menuIds) {
        //先删除角色和菜单的对应关系，然后将传回的新的关系重新添加
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("roleId",roleId);
        roleMenuMapper.delete(roleMenuQueryWrapper);
        // 再把前端传过来的菜单id数组绑定到当前的这个角色id上去
        List<Long> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for (Long menuId : menuIds) {
            Menu menuById = menuService.getById(menuId);
            if (menuById.getPid() != null && !menuIdsCopy.contains(menuById.getPid())){// 二级菜单 并且传过来的menuId数组里面没有它的父级id
                // 那么我们就得补上这个父级id
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuById.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menuById.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }

    }

    @Override
    public List<Long> getRoleMenu(Long roleId) {
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("roleId",roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleMenuQueryWrapper);
        //返回菜单列表ids
        List<Long> menuList = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

        return menuList;
    }

    @Override
    public List<Role> findAll(String name) {
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)){
            roleQueryWrapper.like("name",name);
        }

        return roleMapper.selectList(roleQueryWrapper);
    }
}




