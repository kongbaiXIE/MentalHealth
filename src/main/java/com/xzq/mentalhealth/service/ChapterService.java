package com.xzq.mentalhealth.service;

import com.xzq.mentalhealth.model.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.mentalhealth.model.vo.ChapterVO;

import java.util.List;

/**
* @author 谢志强
* @description 针对表【chapter(课程章节)】的数据库操作Service
* @createDate 2023-01-23 21:23:19
*/
public interface ChapterService extends IService<Chapter> {

    Integer addChapter(Chapter chapter);

    Integer editChapter(Chapter chapter);

    List<ChapterVO> getChapterVideo(long courseId);
}
