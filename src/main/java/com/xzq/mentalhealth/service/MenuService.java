package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.model.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 谢志强
* @description 针对表【menu(菜单表)】的数据库操作Service
* @createDate 2023-01-08 22:00:53
*/
public interface MenuService extends IService<Menu> {
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    Page<Menu> MenuList(long pageNum, long pageSize, String name);

    /**
     * 添加菜单
     * @param Menu
     * @return
     */
    Integer addMenu(Menu Menu);

    /**
     * 修改菜单
     * @param Menu
     * @return
     */
    Integer editMenu(Menu Menu);

}
