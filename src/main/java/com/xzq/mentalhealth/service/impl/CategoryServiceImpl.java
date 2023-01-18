package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.CategoryMapper;
import com.xzq.mentalhealth.model.entity.Category;
import com.xzq.mentalhealth.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 谢志强
* @description 针对表【type(测评类型表)】的数据库操作Service实现
* @createDate 2023-01-17 21:53:03
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Integer editCategory(Category category) {
        return categoryMapper.updateById(category);
    }

    @Override
    public Integer addCategory(Category category) {
        String categoryName = category.getName();
        if (categoryName == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"名字不能为空");
        }
        return categoryMapper.insert(category);
    }

    /**
     * 返回测评类型列表
     * @param name
     * @return
     */
    @Override
    public List<Category> findAll(String name) {
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)){
            categoryQueryWrapper.like("name",name);
        }

        return categoryMapper.selectList(categoryQueryWrapper);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<Category> categoryList(long pageNum, long pageSize, String name) {
        Page<Category> categoryPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.orderByAsc("id");
        if (!"".equals(name)) {
            categoryQueryWrapper.like("name", name);
        }
        return categoryMapper.selectPage(categoryPage, categoryQueryWrapper);
    }
}




