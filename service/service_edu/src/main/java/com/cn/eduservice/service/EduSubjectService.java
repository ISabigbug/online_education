package com.cn.eduservice.service;

import com.cn.eduservice.domain.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.eduservice.domain.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【edu_subject(课程科目)】的数据库操作Service
* @createDate 2023-03-14 21:15:45
*/
public interface EduSubjectService extends IService<EduSubject> {
    //添加课程分类
    void saveSubject(EduSubjectService eduSubjectService,MultipartFile file);

    //查询所有课程分类
    List<OneSubject> findAllSubject();
}
