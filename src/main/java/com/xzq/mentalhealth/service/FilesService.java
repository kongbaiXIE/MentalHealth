package com.xzq.mentalhealth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.mentalhealth.model.entity.Files;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author 谢志强
* @description 针对表【files(文件)】的数据库操作Service
* @createDate 2023-01-03 18:41:13
*/
public interface FilesService extends IService<Files> {

    String uploadFile(MultipartFile file) throws IOException;

    void downloadFiles(String fileUUID, HttpServletResponse response) throws IOException;

    Page<Files> fileList(long pageNum, long pageSize, String name);

    Integer editFile(Files file);
}
