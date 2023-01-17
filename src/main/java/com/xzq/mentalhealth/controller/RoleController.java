package com.xzq.mentalhealth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Menu;
import com.xzq.mentalhealth.model.entity.Role;
import com.xzq.mentalhealth.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  角色接口
 */
@Api(tags = "角色模块")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<Role>> pageRole(@RequestParam long pageNum,
                                             @RequestParam long pageSize,
                                             @RequestParam(defaultValue = "") String name) {
        if (pageNum < 0 && pageSize<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Role> roleList = roleService.roleList(pageNum, pageSize, name);
        if (roleList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(roleList);
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
        boolean removeById = roleService.removeById(id);
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
        boolean removeBatchByIds = roleService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<Integer> addRole(@RequestBody Role role){
        if (role == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = roleService.addRole(role);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditRole(@RequestBody Role role){
        if (role == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = roleService.editRole(role);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 将角色绑定对应的菜单
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public  BaseResponse<Integer> roleMenu(@PathVariable Long roleId,@RequestBody List<Long> menuIds){
        if (roleId < 0 || menuIds == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        roleService.addRoleMenu(roleId,menuIds);

        return ResultUtil.success(1);
    }

    /**
     * 通过roleId 查看menuId列表
     * @param roleId
     * @return
     */
    @GetMapping("/roleMenu/{roleId}")
    public BaseResponse<List<Long>> getRoleMenu(@PathVariable Long roleId) {
       if (roleId < 0){
           throw new BusinessException(ErrorCode.SYSTEM_ERROR);
       }
        List<Long> menuIdList = roleService.getRoleMenu(roleId);
        if (menuIdList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(menuIdList);
    }


    /**
     * 查看角色列表
     * @param name
     * @return
     */
    @GetMapping("/findAll")
    public BaseResponse<List<Role>> findAll(@RequestParam(defaultValue = " ") String name){
        List<Role> roleList = roleService.findAll(name);
        if (roleList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(roleList);
    }

}
