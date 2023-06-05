package com.xzq.mentalhealth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Category;
import com.xzq.mentalhealth.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测评题目接口
 */
@Api(tags = "测评类型模块")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<Category>> pageQuestionCategory(@RequestParam long pageNum,
                                                     @RequestParam long pageSize,
                                                     @RequestParam(defaultValue = "") String name) {
        if (pageNum < 0 && pageSize<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Category> QuestionCategoryList = categoryService.categoryList(pageNum, pageSize, name);
        if (QuestionCategoryList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(QuestionCategoryList);
    }

    /**
     * 查看测评类型
     * @param name
     * @return
     */
    @GetMapping("/findAll")
    public BaseResponse<List<Category>> findAll(@RequestParam(defaultValue = " ") String name){
        if(name == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Category> QuestionCategoryList = categoryService.findAll(name);
        if (QuestionCategoryList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(QuestionCategoryList);
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
        boolean removeById = categoryService.removeById(id);
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
        boolean removeBatchByIds = categoryService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 添加测评类型
     * @param Category
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<Integer> addQuestionCategory(@RequestBody Category Category){
        if (Category == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = categoryService.addCategory(Category);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 修改测评类型
     * @param Category
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditQuestionCategory(@RequestBody Category Category){
        if (Category == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = categoryService.editCategory(Category);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }

}
