package com.xzq.mentalhealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzq.mentalhealth.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 谢志强
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2022-12-31 19:04:36
* @Entity com.xzq.mentalhealth.model.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




