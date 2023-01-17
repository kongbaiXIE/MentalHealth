package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.mentalhealth.model.entity.Menu;
import com.xzq.mentalhealth.model.entity.Role;

import java.util.List;

/**
* @author 谢志强
* @description 针对表【role(角色表)】的数据库操作Service
* @createDate 2022-12-31 20:29:39
*/
public interface RoleService extends IService<Role> {
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    Page<Role> roleList(long pageNum, long pageSize, String name);

    /**
     * 添加角色
     * @param role
     * @return
     */
    Integer addRole(Role role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    Integer editRole(Role role);

    /**
     *将角色绑定对应的菜单
     * @param roleId
     * @param menuIds
     */
    void addRoleMenu(Long roleId, List<Long> menuIds);

    List<Long> getRoleMenu(Long roleId);

    /**
     * 查看role列表
     * @param name
     * @return
     */
    List<Role> findAll(String name);
}
