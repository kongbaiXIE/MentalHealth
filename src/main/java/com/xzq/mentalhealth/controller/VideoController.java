package com.xzq.mentalhealth.controller;

import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Video;
import com.xzq.mentalhealth.service.VideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 课程小节管理
 */
@Api(tags = "课程小节模块")
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 根据id查询数据
     */
    @GetMapping("/findBYId/{id}")
    public BaseResponse<Video> getById(@PathVariable long id){
        if (id < 0){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Video videoServiceById = videoService.getById(id);
        return ResultUtil.success(videoServiceById);
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
        boolean removeById = videoService.removeById(id);
        if (!removeById){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 添加题目
     * @param video
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<Integer> addQuestion(@RequestBody Video video){
        if (video == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = videoService.addVideo(video);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 修改菜单
     * @param video
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditQuestion(@RequestBody Video video){
        if (video == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = videoService.editVideo(video);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }

}
