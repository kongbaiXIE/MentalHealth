package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.model.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 谢志强
* @description 针对表【type(测评类型表)】的数据库操作Service
* @createDate 2023-01-17 21:53:03
*/
public interface CategoryService extends IService<Category> {

    Page<Category> categoryList(long pageNum, long pageSize, String name);

    List<Category> findAll(String name);

    Integer addCategory(Category category);

    Integer editCategory(Category category);
}
