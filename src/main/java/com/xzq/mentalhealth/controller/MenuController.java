package com.xzq.mentalhealth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.DictMapper;
import com.xzq.mentalhealth.model.entity.Dict;
import com.xzq.mentalhealth.model.entity.Menu;
import com.xzq.mentalhealth.service.MenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  菜单接口
 */
@Api(tags = "菜单模块")
@RestController
@RequestMapping("/menu")
public class MenuController {


    @Autowired
    private MenuService menuService;

    @Autowired
    private DictMapper dictMapper;
    ///**
    // * 分页查询
    // * @param pageNum
    // * @param pageSize
    // * @param name
    // * @return
    // */
    //@GetMapping("/page")
    //public BaseResponse<Page<Menu>> pageMenu(@RequestParam long pageNum,
    //                                         @RequestParam long pageSize,
    //                                         @RequestParam(defaultValue = "") String name) {
    //    if (pageNum < 0 && pageSize<0){
    //        throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //    }
    //    Page<Menu> MenuList = menuService.MenuList(pageNum, pageSize, name);
    //    if (MenuList == null){
    //        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    //    }
    //
    //    return ResultUtil.success(MenuList);
    //}

    /**
     * 查看菜单列表
     * @param name
     * @return
     */
    @GetMapping("/findAll")
    public BaseResponse<List<Menu>> findAll(@RequestParam(defaultValue = " ") String name){
        if(name == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Menu> menuList = menuService.findAll(name);
        if (menuList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(menuList);
    }
    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/del/{id}")
    public BaseResponse<Boolean> deleteById(@PathVariable long id){
        if (id <=0 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean removeById = menuService.removeById(id);
        if (!removeById){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 批量删除数据
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public BaseResponse<Boolean>  deleteBatch(@RequestBody List<Long> ids){
        if (ids == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean removeBatchByIds = menuService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 添加菜单
     * @param Menu
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<Integer> addMenu(@RequestBody Menu Menu){
        if (Menu == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = menuService.addMenu(Menu);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 修改菜单
     * @param Menu
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditMenu(@RequestBody Menu Menu){
        if (Menu == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = menuService.editMenu(Menu);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 图标数据列表
     * @return list<Dict>
     */
    @GetMapping("/icons")
    public BaseResponse<List<Dict>> getIcons() {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", "icon");
        List<Dict> dictList = dictMapper.selectList(queryWrapper);
        return ResultUtil.success(dictList);
    }

    /**
     * 查询菜单所以id
     * @return
     */
    @GetMapping("/ids")
    public BaseResponse<List<Long>> findAllIds() {
        List<Long> menuList = menuService.list().stream().map(Menu::getId).collect(Collectors.toList());
        return ResultUtil.success(menuList);
    }

}
