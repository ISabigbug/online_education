package com.cn.eduservice.controller.front;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.commonutils.JwtUtils;
import com.cn.commonutils.Result;
import com.cn.commonutils.ordervo.Course;
import com.cn.eduservice.client.OrderClient;
import com.cn.eduservice.domain.EduCourse;
import com.cn.eduservice.domain.chapter.Chapter;
import com.cn.eduservice.domain.frontvo.CourseQueryVo;
import com.cn.eduservice.domain.frontvo.CourseWebVo;
import com.cn.eduservice.service.EduChapterService;
import com.cn.eduservice.service.EduCourseService;
import com.cn.servicebase.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.controller
 * @Description:
 */
@RestController
@RequestMapping("/eduservice/courseFront")
@CrossOrigin
public class EduCourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    //1.条件分页查询课程信息
    @PostMapping("pageCourseFront/{page}/{limit}")
    public Result pageCourseFront(@PathVariable long page, @PathVariable long limit,
                                  @RequestBody(required = false) CourseQueryVo CourseQuery) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> courseFrontList = eduCourseService.pageCourseFront(pageCourse, CourseQuery);
        return Result.success().data(courseFrontList);
    }

    //2.获取课程基本信息
    @GetMapping("getBaseCourseInfo/{cid}")
    public Result getBaseCourseInfo(@PathVariable String cid, HttpServletRequest request) {
        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = eduCourseService.getcourseWebVo(cid);
        //查询当前课程的章节信息
        List<Chapter> chapterList = eduChapterService.findChapterVideo(cid);
        //通过用户ID和课程ID判断是否购买该课程
        boolean isBuyCourse = orderClient.isBuyCourse(cid, JwtUtils.getMemberIdByJwtToken(request));

        return Result.success().data("courseWebVo", courseWebVo).data("chapterList", chapterList).data("isBuyCourse", isBuyCourse);
    }

    //3.根据课程ID获取课程信息
    @GetMapping("getInfoByCid/{cid}")
    public Course getInfoByCid(@PathVariable String cid) {
        Course course = eduCourseService.getInfoByCid(cid);
        return course;
    }

    //4.判断用户是否登录
    @GetMapping("isLogin")
    public Result isLogin(HttpServletRequest request) {
        String menberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isBlank(menberId)) {
            return Result.error();
        } else {
            return Result.success();
        }
    }
}
