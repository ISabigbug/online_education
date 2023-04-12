package com.cn.eduservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.eduservice.domain.EduVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author Lenovo
 * @description 针对表【edu_video(课程视频)】的数据库操作Mapper
 * @createDate 2023-03-19 15:35:50
 * @Entity com.cn.eduservice.domain.EduVideo
 */
public interface EduVideoMapper extends BaseMapper<EduVideo> {
    int deleteByCourseId(@Param("courseId") String courseId);
}




