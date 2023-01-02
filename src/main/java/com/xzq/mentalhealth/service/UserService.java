package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.mentalhealth.model.entity.User;
import com.xzq.mentalhealth.model.vo.UserVO;

import java.util.List;


/**
* @author 谢志强
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2022-12-31 19:04:36
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */

    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     *  用户登录
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @return 用户
     */
    User userLogin(String userAccount, String userPassword);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User safetyUser(User originUser);

    /**
     * 修改用户
     * @param user
     * @return
     */
    Integer editUser(User user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    Integer addUser(User user);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param username
     * @param email
     * @param phone
     * @return
     */
    Page<User> userList(long pageNum, long pageSize, String username, String email, String phone);
}
