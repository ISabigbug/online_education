package com.cn.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.commonutils.Result;
import com.cn.eduservice.domain.EduTeacher;
import com.cn.eduservice.service.EduTeacherService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/eduservice/teacherFront"})
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;

    public TeacherFrontController() {
    }

    @GetMapping({"pageTeacherFront/{page}/{limit}"})
    public Result pageTeacherFront(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page(page, limit);
        Map<String, Object> teacherFrontList = this.eduTeacherService.pageTeacherFront(pageTeacher);
        return Result.success().data(teacherFrontList);
    }
}
