package com.xzq.mentalhealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.MenuMapper;
import com.xzq.mentalhealth.mapper.RoleMapper;
import com.xzq.mentalhealth.mapper.RoleMenuMapper;
import com.xzq.mentalhealth.mapper.UserMapper;
import com.xzq.mentalhealth.model.entity.Menu;
import com.xzq.mentalhealth.model.entity.Role;
import com.xzq.mentalhealth.model.entity.RoleMenu;
import com.xzq.mentalhealth.model.entity.User;
import com.xzq.mentalhealth.model.vo.UserVO;
import com.xzq.mentalhealth.service.MenuService;
import com.xzq.mentalhealth.service.RoleService;
import com.xzq.mentalhealth.service.UserService;
import com.xzq.mentalhealth.untils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
* @author 谢志强
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2022-12-31 19:04:36
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    UserMapper userMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    RoleMenuMapper roleMenuMapper;
    @Resource
    RoleService roleService;
    @Resource
    MenuService menuService;
    /**
     * 盐值 加密密码
     */
    private static final String SALT = "XZQ";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if (userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }
        if(userPassword.length()<8 || checkPassword.length() < 8 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }

        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()){
            return -1;
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号重复");
        }
        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));

        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        //todo 感觉不需要在添加枚举值或者一个常量类，如果使用多的话可以考虑
        user.setRole("ROLE_USER");
        boolean saveResult = this.save(user);
        if (!saveResult){
            return -1;
        }
        return user.getId();
    }

    /**
     * 用户登陆
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @return
     */
    @Override
    public UserVO userLogin(String userAccount, String userPassword) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if (userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }
        if(userPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }
        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号不能包含特殊字符");
        }
        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user or password error");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码错误");
        }

        // 生成token
        String token = TokenUtils.genToken(user);
        UserVO userVO = new UserVO();
        //将用户数据拷贝到userVO上
        BeanUtils.copyProperties(user,userVO);
        String flag = userVO.getRole();
        //通过用户表中的角色唯一标识查询出对应的角色
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("flag",flag);
        Role role = roleMapper.selectOne(roleQueryWrapper);
        if (role == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //通过该角色id查询对应的菜单id列表
        Long roleId = role.getId();
        //由于在之前已经有通过roleId查询相应的菜单id集合所以直接调用
        List<Long> menuIdList = roleService.getRoleMenu(roleId);
        //查询菜单表中的数据
        List<Menu> menus = menuService.findAll("");
        // 返回最后该角色对应的菜单列表
        List<Menu> roleMenus = new ArrayList<>();
        for (Menu menu : menus) {
            if (menuIdList.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            children.removeIf(child -> !menuIdList.contains(child.getId()));
        }

        userVO.setMenuList(roleMenus);
        userVO.setToken(token);
        ////脱敏
        return userVO;
    }



    /**
     * 管理员修改用户
     * @param user
     * @return
     */
    @Override
    public Integer editUser(User user) {
        //通过id查询出用户
        Long userId = user.getId();
        //拿到数据库中的用户数据
        User oldUser = userMapper.selectById(userId);
        if (Objects.equals(oldUser.getUsername(), user.getUsername())){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户昵称未发生改变");
        }
        return userMapper.updateById(user);
    }

    /**
     * 管理员添加用户
     * @param user
     * @return
     */
    @Override
    public Integer addUser(User user) {
       if (user.getUserAccount()==null && user.getUsername() == null){
           throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号和昵称不能为空");
       }
        //管理员添加的用户设置默认密码为 12345678
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + "12345678").getBytes(StandardCharsets.UTF_8));
        user.setUserPassword(encryptPassword);
        //添加默认头像路径
        user.setAvatarUrl("https://www.dmoe.cc/random.php");

        return userMapper.insert(user);
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
    @Override
    public Page<User> userList(long pageNum, long pageSize, String username, String email, String phone) {
        Page<User> userPage = new Page<>(pageNum, pageSize);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.orderByAsc("id");
        if (!"".equals(username)) {
            userQueryWrapper.like("username", username);
        }
        if (!"".equals(email)) {
            userQueryWrapper.like("email", email);
        }
        if (!"".equals(phone)) {
            userQueryWrapper.like("address", phone);
        }
        return userMapper.selectPage(userPage, userQueryWrapper);
    }

    @Override
    public UserVO findByUserAccount(String userAccount) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        User user = userMapper.selectOne(queryWrapper);
        UserVO userVO = new UserVO();
        //将用户数据拷贝到userVO上
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }
}




