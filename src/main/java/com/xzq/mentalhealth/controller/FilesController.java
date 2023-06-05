package com.xzq.mentalhealth.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Files;
import com.xzq.mentalhealth.service.FilesService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(tags = "文件模块")
@RestController
@RequestMapping("/files")
public class FilesController {


    @Autowired
    private FilesService filesService;
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<Page<Files>> pageQuestion(@RequestParam long pageNum,
                                                   @RequestParam long pageSize,
                                                   @RequestParam(defaultValue = "") String name) {
        if (pageNum < 0 && pageSize<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        Page<Files> courseList = filesService.fileList(pageNum, pageSize,name);
        if (courseList == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtil.success(courseList);
    }
    /**
     * 文件上传接口
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public BaseResponse<String> upload(@RequestParam MultipartFile file) throws IOException {

        if (file == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"上传文件为空");
        }
        String url= filesService.uploadFile(file);
        if (url == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件上传失败");
        }
        return ResultUtil.success(url);
    }

    /**
     * 文件下载接口
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        if (fileUUID == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        filesService.downloadFiles(fileUUID,response);
    }
    /**
     * 根据id删除
     * @param id
     * @return
     */
    @PostMapping("/del/{id}")
    public BaseResponse<Boolean> deleteById(@PathVariable long id){
        if (id <=0 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean removeById = filesService.removeById(id);
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
        boolean removeBatchByIds = filesService.removeBatchByIds(ids);
        if (!removeBatchByIds){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 修改文件状态
     * @param file
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Integer> EditQuestion(@RequestBody Files file){
        if (file == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = filesService.editFile(file);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }
}
