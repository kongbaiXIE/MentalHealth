package com.xzq.mentalhealth.controller;


import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.service.FilesService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = "文件模块")
@RestController
@RequestMapping("/files")
public class FilesController {


    @Autowired
    private FilesService filesService;

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

}
