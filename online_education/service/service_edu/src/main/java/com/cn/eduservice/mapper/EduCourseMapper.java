package com.cn.eduservice.mapper;
import com.cn.eduservice.domain.frontvo.CourseWebVo;
import org.apache.ibatis.annotations.Param;

import com.cn.eduservice.domain.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.eduservice.domain.vo.CoursePublishVo;

/**
* @author Lenovo
* @description 针对表【edu_course(课程)】的数据库操作Mapper
* @createDate 2023-03-19 15:20:54
* @Entity com.cn.eduservice.domain.EduCourse
*/
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo findCoursePublishVo(@Param("cid") String cid);

    int updateStatusById(@Param("status") String status, @Param("id") String id);

    CourseWebVo getcourseWebVo(@Param("cid") String cid);
}




