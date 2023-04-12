package com.cn.eduservice.service;

import com.cn.eduservice.domain.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Lenovo
* @description 针对表【edu_course_description(课程简介)】的数据库操作Service
* @createDate 2023-03-19 15:21:41
*/
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    void deleteDescByCourseId(String cid);
}
