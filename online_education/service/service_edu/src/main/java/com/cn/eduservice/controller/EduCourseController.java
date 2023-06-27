package com.cn.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.commonutils.Result;
import com.cn.eduservice.domain.EduCourse;
import com.cn.eduservice.domain.EduTeacher;
import com.cn.eduservice.domain.vo.CoursePublishVo;
import com.cn.eduservice.domain.vo.CourseQuery;
import com.cn.eduservice.domain.vo.CourseVo;
import com.cn.eduservice.domain.vo.TeacherQuery;
import com.cn.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.controller
 * @Description:
 */
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //1.添加课程信息
    @PostMapping("saveCourseInfo")
    public Result saveCourseInfo(@RequestBody CourseVo courseVo) {
        //返回课程id为添加课程大纲使用
        String cid = eduCourseService.saveCourseInfo(courseVo);
        return Result.success().data("cid", cid);
    }

    //2.根据课程ID查询课程基本信息
    @GetMapping("findCourseInfo/{cid}")
    public Result findCourseInfo(@PathVariable String cid) {
        CourseVo courseVo = eduCourseService.findCourseInfo(cid);
        return Result.success().data("courseVo", courseVo);
    }

    //3.修改课程信息
    @PostMapping("updateConseInfo")
    public Result updateConseInfo(@RequestBody CourseVo courseVo) {
        eduCourseService.updateConseInfo(courseVo);
        return Result.success();
    }

    //4.通过课程ID查询课程发布信息
    @GetMapping("findCoursePublishVo/{cid}")
    public Result findCoursePublishVo(@PathVariable String cid) {
        CoursePublishVo coursePublishVo = eduCourseService.findCoursePublishVo(cid);
        if (coursePublishVo != null) {
            return Result.success().data("coursePublishVo", coursePublishVo);
        } else {
            return Result.error();
        }
    }

    //5.课程最终发布
    @PostMapping("publishCourse/{cid}")
    public Result publishCourse(@PathVariable String cid) {
        boolean res = eduCourseService.updateStatusById("Normal", cid);
        if (res) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    //6.分页查询课程信息
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public Result pageCourseCondition(@PathVariable long current, @PathVariable long limit,
                                      @RequestBody(required = false) CourseQuery courseQuery) {
        //创建page对象
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        //组装条件
        LambdaQueryWrapper<EduCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(courseQuery.getTitle()), EduCourse::getTitle, courseQuery.getTitle())
                .eq(StringUtils.isNotBlank(courseQuery.getSubjectParentId()), EduCourse::getSubjectParentId, courseQuery.getSubjectParentId())
                .eq(StringUtils.isNotBlank(courseQuery.getSubjectId()), EduCourse::getSubjectId, courseQuery.getSubjectId())
                .orderByDesc(EduCourse::getGmtCreate);
        //调用方法实现条件查询分页
        eduCourseService.page(pageCourse, wrapper);
        long total = pageCourse.getTotal();//记录数
        List<EduCourse> records = pageCourse.getRecords();//总数据
        return Result.success().data("total", total).data("records", records);
    }

    //7.根据课程ID删除课程信息
    @DeleteMapping("removeCourseByCid/{cid}")
    public Result removeCourseByCid(@PathVariable String cid) {
        eduCourseService.removeCourseByCid(cid);
        return Result.success();
    }
}
