package com.cn.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.commonutils.Result;
import com.cn.eduservice.domain.EduCourse;
import com.cn.eduservice.domain.EduTeacher;
import com.cn.eduservice.service.EduCourseService;
import com.cn.eduservice.service.EduTeacherService;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/eduservice/teacherFront"})
public class TeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    //1.讲师分页
    @GetMapping({"pageTeacherFront/{page}/{limit}"})
    public Result pageTeacherFront(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page(page, limit);
        Map<String, Object> teacherFrontList = eduTeacherService.pageTeacherFront(pageTeacher);
        return Result.success().data(teacherFrontList);
    }

    //2.根据讲师ID查询讲师详情
    @GetMapping("getInfoByTeacherId/{teacherId}")
    public Result getInfoByTeacherId(@PathVariable String teacherId) {
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);
        List<EduCourse> courseList = eduCourseService.getCourseByTeacherId(teacherId);
        return Result.success().data("eduTeacher",eduTeacher).data("courseList",courseList);
    }
}
