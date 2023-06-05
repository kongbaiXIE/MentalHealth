package com.xzq.mentalhealth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzq.mentalhealth.common.BaseResponse;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.common.ResultUtil;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.model.entity.Chapter;
import com.xzq.mentalhealth.model.entity.Video;
import com.xzq.mentalhealth.model.vo.ChapterVO;
import com.xzq.mentalhealth.service.ChapterService;
import com.xzq.mentalhealth.service.VideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 课程章节管理
 */
@Api(tags = "课程章节模块")
@RestController
@RequestMapping("/chapter")
public class
ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private VideoService videoService;

    //课程大纲列表,根据课程id进行查询
    @GetMapping("/getChapterVideo/{courseId}")
    private BaseResponse<List<ChapterVO>> getChapterVideo(@PathVariable long courseId){
        if (courseId < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<ChapterVO> chapterVideo = chapterService.getChapterVideo(courseId);

        if (chapterVideo == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(chapterVideo);
    }

    /**
     * 根据id查询章节数据
     * @param chapterId
     * @return
     */
    @GetMapping("/{chapterId}")
    private BaseResponse<Chapter> getChapterById(@PathVariable long chapterId){
        if (chapterId < 0){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Chapter chapterById = chapterService.getById(chapterId);
        return ResultUtil.success(chapterById);
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
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapterId",id);
        videoService.remove(videoQueryWrapper);
        boolean removeById = chapterService.removeById(id);
        if (!removeById){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtil.success(true);
    }

    /**
     * 添加题目
     * @param chapter
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<Integer> addQuestion(@RequestBody Chapter chapter){
        if (chapter == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = chapterService.addChapter(chapter);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加失败");
        }
        return ResultUtil.success(integer);
    }

    /**
     * 修改菜单
     * @param chapter
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Integer> EditQuestion(@RequestBody Chapter chapter){
        if (chapter == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer integer = chapterService.editChapter(chapter);
        if (integer < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return ResultUtil.success(integer);
    }

}
