package com.cn.eduservice.mapper;
import org.apache.ibatis.annotations.Param;

import com.cn.eduservice.domain.EduChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Lenovo
* @description 针对表【edu_chapter(课程)】的数据库操作Mapper
* @createDate 2023-03-19 15:36:13
* @Entity com.cn.eduservice.domain.EduChapter
*/
public interface EduChapterMapper extends BaseMapper<EduChapter> {

    int deleteByCourseId(@Param("courseId") String courseId);
}




