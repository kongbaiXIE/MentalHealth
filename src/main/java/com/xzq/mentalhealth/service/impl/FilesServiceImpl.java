package com.xzq.mentalhealth.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Files;
import com.xzq.mentalhealth.service.FilesService;
import com.xzq.mentalhealth.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
* @author 谢志强
* @description 针对表【files(文件)】的数据库操作Service实现
* @createDate 2023-01-03 18:41:13
*/
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files>
    implements FilesService{
    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Value("${server.ip}")
    private String serverIp;
    @Resource
    private FilesMapper filesMapper;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件类型
        String type = FileUtil.extName(originalFilename);
        // 获取文件大小
        long size = file.getSize();
        //定义一个文件唯一的标识码
        String fileUUID = IdUtil.fastSimpleUUID()+ StrUtil.DOT+ type;
        File uploadFile = new File(fileUploadPath + fileUUID);
        //判断配置文件目录是否存在,若不存在则创建一个新的目录
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        //返回图片路径
        String url;
        //获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        //从数据库查询是否存在相同的记录
        Files filesByMD5 = getFilesByMD5(md5);
        if (filesByMD5 != null){
            url = filesByMD5.getUrl();
        }else {
            //上传文件到本地磁盘
            file.transferTo(uploadFile);
            //如果数据库不存在重复文件,则不删除刚才上传的文件
            url = "http://" +serverIp+":8080/files/"+fileUUID;
        }
        //存储数据库
        Files saveFiles = new Files();
        saveFiles.setName(originalFilename);
        saveFiles.setType(type);
        saveFiles.setSize(size/1024);
        saveFiles.setUrl(url);
        saveFiles.setMd5(md5);
        int insert = filesMapper.insert(saveFiles);
        if (insert < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件上传失败");
        }
        return url;
    }

    /**
     * 文件下载接口   http://localhost:8080/files/{fileUUID}
     * @param fileUUID
     * @param response
     */
    @Override
    public void downloadFiles(String fileUUID, HttpServletResponse response) throws IOException {
        //根据文件的唯一标识码获取文件
        File downloadFiles = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(downloadFiles));
        os.flush();
        os.close();
    }

    /**
     * 通过文件的md5查询文件
     * @param md5
     * @return
     */
    private Files getFilesByMD5(String md5){
        QueryWrapper<Files> filesQueryWrapper = new QueryWrapper<>();
        filesQueryWrapper.eq("md5",md5);
        List<Files> files = filesMapper.selectList(filesQueryWrapper);
        return files.size() == 0 ? null:files.get(0);
    }
}




