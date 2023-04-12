package com.cn.eduservice.controller;

import com.cn.commonutils.Result;
import com.cn.eduservice.domain.subject.OneSubject;
import com.cn.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.controller
 * @Description:
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //1.添加头像
    @PostMapping("addSubject")
    public Result addSubject(MultipartFile file){
        eduSubjectService.saveSubject(eduSubjectService,file);
        return Result.success();
    }

    //2.查询所有课程分类
    @GetMapping("findAllSubject")
    public Result findAllSubject(){
        List<OneSubject> subjectList = eduSubjectService.findAllSubject();
        return Result.success().data("subjectList",subjectList);
    }
}
