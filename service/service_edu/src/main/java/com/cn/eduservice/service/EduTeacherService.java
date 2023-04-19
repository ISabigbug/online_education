package com.cn.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.eduservice.domain.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author Lenovo
* @description 针对表【edu_teacher(讲师)】的数据库操作Service
* @createDate 2023-02-27 11:01:50
*/
public interface EduTeacherService extends IService<EduTeacher> {
    List<EduTeacher> findFourTeacher();

    Map<String, Object> pageTeacherFront(Page<EduTeacher> pageTeacher);
}
