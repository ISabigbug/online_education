package com.cn.eduservice.service;

import com.cn.eduservice.domain.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.eduservice.domain.chapter.Chapter;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【edu_chapter(课程)】的数据库操作Service
* @createDate 2023-03-19 15:36:13
*/
public interface EduChapterService extends IService<EduChapter> {

    List<Chapter> findChapterVideo(String cid);

    boolean deleteChapterById(String chapterId);

    void deleteChapterByCourseId(String cid);
}
