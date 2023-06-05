package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.MenuMapper;
import com.xzq.mentalhealth.model.entity.Menu;
import com.xzq.mentalhealth.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 谢志强
* @description 针对表【menu(菜单表)】的数据库操作Service实现
* @createDate 2023-01-08 22:00:53
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Resource
    MenuMapper menuMapper;
    @Override
    public Page<Menu> MenuList(long pageNum, long pageSize, String name) {
        Page<Menu> userPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Menu> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.orderByAsc("id");
        if (!"".equals(name)) {
            userQueryWrapper.like("name", name);
        }
        return menuMapper.selectPage(userPage, userQueryWrapper);
    }

    /**
     * 添加菜单
     * @param Menu
     * @return
     */
    @Override
    public Integer addMenu(Menu Menu) {
        if (Menu.getName()==null && Menu.getIcon() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"菜单名称和图标不能为空");
        }
        return menuMapper.insert(Menu);
    }

    /**
     * 修改菜单
     * @param Menu
     * @return
     */
    @Override
    public Integer editMenu(Menu Menu) {
        return menuMapper.updateById(Menu);
    }

    @Override
    public List<Menu> findAll(String name) {
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.orderByAsc("sort_num");
        if (StrUtil.isNotBlank(name)){
            menuQueryWrapper.like("name",name);
        }
        // 查询所有数据
        List<Menu> menuList = menuMapper.selectList(menuQueryWrapper);
        // 找出pid为null的一级菜单
        List<Menu> parentNodes = menuList.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (Menu parentNode : parentNodes) {
            //筛选所有数据中pid=父级id的数据就是二级菜单
            parentNode.setChildren(menuList.stream().filter(menu -> parentNode.getId().equals(menu.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}




