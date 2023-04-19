package com.cn.eduservice.controller.front;

import com.cn.commonutils.Result;
import com.cn.eduservice.domain.EduCourse;
import com.cn.eduservice.domain.EduTeacher;
import com.cn.eduservice.service.EduCourseService;
import com.cn.eduservice.service.EduTeacherService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/eduservice/index"})
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    public IndexFrontController() {
    }

    @GetMapping({"index"})
    public Result index() {
        List<EduTeacher> teacherList = this.eduTeacherService.findFourTeacher();
        List<EduCourse> courseList = this.eduCourseService.findEightCourse();
        return Result.success().data("teacherList", teacherList).data("courseList", courseList);
    }
}
