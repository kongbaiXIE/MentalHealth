package com.xzq.mentalhealth.service.impl;

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
import java.util.Objects;

/**
* @author 谢志强
* @description 针对表【menu(菜单表)】的数据库操作Service实现
* @createDate 2023-01-08 22:00:53
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Resource
    MenuMapper MenuMapper;
    @Override
    public Page<Menu> MenuList(long pageNum, long pageSize, String name) {
        Page<Menu> userPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Menu> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.orderByAsc("id");
        if (!"".equals(name)) {
            userQueryWrapper.like("name", name);
        }
        return MenuMapper.selectPage(userPage, userQueryWrapper);
    }

    /**
     * 添加角色
     * @param Menu
     * @return
     */
    @Override
    public Integer addMenu(Menu Menu) {
        if (Menu.getName()==null && Menu.getIcon() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"菜单名称和图标不能为空");
        }
        return MenuMapper.insert(Menu);
    }

    /**
     * 修改角色
     * @param Menu
     * @return
     */
    @Override
    public Integer editMenu(Menu Menu) {
        //通过id查询出用户
        Long MenuId = Menu.getId();
        //拿到数据库中的用户数据
        Menu oldMenu = MenuMapper.selectById(MenuId);
        if (Objects.equals(oldMenu.getName(), Menu.getName())){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"角色名称未发生改变");
        }
        return MenuMapper.updateById(Menu);
    }
}




