package com.xzq.mentalhealth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.User;
import com.xzq.mentalhealth.model.requsest.UserLoginRequest;
import com.xzq.mentalhealth.model.requsest.UserRegisterRequest;
import com.xzq.mentalhealth.model.vo.UserVO;
import com.xzq.mentalhealth.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户接口
 *
 * @author yupi
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtil.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest){
        if (userLoginRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO user = userService.userLogin(userAccount, userPassword);
        return ResultUtil.success(user);
    }
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param username
     * @param email
     * @param phone
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<User>> pageUser(@RequestParam long pageNum,
                                             @RequestParam long pageSize,
                                             @RequestParam(defaultValue = "") String username,
                                             @RequestParam(defaultValue = "") String email,
                                             @RequestParam(defaultValue = "") String phone) {
        if (pageNum < 0 && pageSize<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<User> userList = userService.userList(pageNum, pageSize, username, email, phone);
        if (userList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(userList);
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
        boolean removeById = userService.removeById(id);
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
        boolean removeBatchByIds = userService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<Integer> addUser(@RequestBody User user){
        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = userService.addUser(user);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditUser(@RequestBody User user){
        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = userService.editUser(user);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 通过用户名查找用户
     * @param userAccount
     * @return
     */
    @GetMapping("/userAccount/{userAccount}")
    public BaseResponse<UserVO> findByUserAccount(@PathVariable String userAccount) {
        if(userAccount == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO byUserAccount = userService.findByUserAccount(userAccount);
        if (byUserAccount == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"查询不到该用户");
        }
        return ResultUtil.success(byUserAccount);
    }
}
