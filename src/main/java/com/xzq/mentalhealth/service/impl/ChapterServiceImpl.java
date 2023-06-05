package com.xzq.mentalhealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.mentalhealth.common.ErrorCode;
import com.xzq.mentalhealth.exception.BusinessException;
import com.xzq.mentalhealth.mapper.VideoMapper;
import com.xzq.mentalhealth.model.entity.Chapter;
import com.xzq.mentalhealth.model.entity.Video;
import com.xzq.mentalhealth.model.vo.ChapterVO;
import com.xzq.mentalhealth.model.vo.VideoVO;
import com.xzq.mentalhealth.service.ChapterService;
import com.xzq.mentalhealth.mapper.ChapterMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 谢志强
* @description 针对表【chapter(课程章节)】的数据库操作Service实现
* @createDate 2023-01-23 21:23:19
*/
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter>
    implements ChapterService{

    @Resource
    ChapterMapper chapterMapper;
    @Resource
    VideoMapper videoMapper;

    /**
     * 添加章节
     * @param chapter
     * @return
     */
    @Override
    public Integer addChapter(Chapter chapter) {
        if (chapter == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return chapterMapper.insert(chapter);
    }

    /**
     * 修改章节
     * @param chapter
     * @return
     */
    @Override
    public Integer editChapter(Chapter chapter) {
        return chapterMapper.updateById(chapter);
    }

    /**
     * 根据课程id获取对应的章节和小结信息
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVO> getChapterVideo(long courseId) {
        //根据课程id查询出对应的章节信息
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("courseId",courseId);
        List<Chapter> chapterList = chapterMapper.selectList(chapterQueryWrapper);
        //根据课程id查询课程里面所有的小节
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("courseId",courseId);
        List<Video> videoList = videoMapper.selectList(videoQueryWrapper);
        //创建一个list集合，用于封装数据
        List<ChapterVO> chapterVOList = new ArrayList<>();

        //遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for (Chapter chapter : chapterList) {
            //每个章节
            //Chapter对象值复制到ChapterVo里面
            ChapterVO chapterVo = new ChapterVO();
            BeanUtils.copyProperties(chapter, chapterVo);
            //把chapterVo放到最终list集合
            chapterVOList.add(chapterVo);

            //创建集合，用于封装章节的小节
            List<VideoVO> videoVOList = new ArrayList<>();

            //4 遍历查询小节list集合，进行封装
            for (Video video : videoList) {
                //得到每个小节
                //判断：小节里面chapterId和章节里面id是否一样
                if (video.getChapterId().equals(chapter.getId())) {
                    //进行封装
                    VideoVO videoVo = new VideoVO();
                    BeanUtils.copyProperties(video, videoVo);
                    //放到小节封装集合
                    videoVOList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoVOList);
        }
        return chapterVOList;
    }

}




