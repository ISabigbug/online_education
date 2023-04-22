package com.cn.eduservice.service;

import com.cn.eduservice.domain.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.eduservice.domain.vo.CoursePublishVo;
import com.cn.eduservice.domain.vo.CourseVo;

import java.util.List;

/**
 * @author Lenovo
 * @description 针对表【edu_course(课程)】的数据库操作Service
 * @createDate 2023-03-19 15:20:54
 */
public interface EduCourseService extends IService<EduCourse> {
    //添加课程信息
    String saveCourseInfo(CourseVo courseVo);

    //2.根据课程ID查询课程基本信息
    CourseVo findCourseInfo(String cid);

    //3.修改课程信息
    void updateConseInfo(CourseVo courseVo);

    //4.通过课程ID查询课程发布信息
    CoursePublishVo findCoursePublishVo(String cid);

    //5.最终发布课程
    boolean updateStatusById(String status, String cid);

    //7.根据课程ID删除课程信息
    void removeCourseByCid(String cid);

    //8.查询8条热门课程数据
    List<EduCourse> findEightCourse();

    //9.根据讲师ID查询授课信息
    List<EduCourse> getCourseByTeacherId(String teacherId);
}
