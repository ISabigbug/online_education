package com.cn.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.commonutils.Result;
import com.cn.eduservice.domain.EduTeacher;
import com.cn.eduservice.domain.vo.TeacherQuery;
import com.cn.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.controller
 * @Description:
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //把service注入
    @Autowired
    private EduTeacherService eduTeacherService;

    //1.查询所有讲师数据
    @GetMapping("findAll")
    public Result findAll(){
        //调用service的方法实现查询所有的操作
        List<EduTeacher> teachers = eduTeacherService.list(null);
        return Result.success().data("teachers",teachers);
    }

    //2.逻辑删除讲师
    @DeleteMapping("{id}")
    public Result removeTeacher(@PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    //3.分页查询讲师
    @GetMapping("pageListTeacher/{current}/{limit}")
    public Result pageListTeacher(@PathVariable long current,
                                  @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //调用方法实现分页,底层会把数据封装到pageTeacher对象里
        eduTeacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//总数据
        return Result.success().data("total",total).data("records",records);
    }

    //4.条件查询分页
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                       @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //组装条件
        LambdaQueryWrapper<EduTeacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(teacherQuery.getName()),EduTeacher::getName,teacherQuery.getName())
                .eq(teacherQuery.getLevel() != null,EduTeacher::getLevel,teacherQuery.getLevel())
                .ge(StringUtils.isNotBlank(teacherQuery.getBegin()),EduTeacher::getGmtCreate,teacherQuery.getBegin())
                .le(StringUtils.isNotBlank(teacherQuery.getEnd()),EduTeacher::getGmtCreate,teacherQuery.getEnd())
                .orderByDesc(EduTeacher::getGmtCreate);
        //调用方法实现条件查询分页
        eduTeacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();//记录数
        List<EduTeacher> records = pageTeacher.getRecords();//总数据
        return Result.success().data("total",total).data("records",records);
    }

    //5.添加讲师
    @PostMapping("addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher){
        //调用方法实现添加
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    //6.根据id查询讲师
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable String id){
        //调用方法实现根据id查询
        EduTeacher teacher = eduTeacherService.getById(id);
        return Result.success().data("teacher",teacher);
    }

    //7.根据id修改讲师信息
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher){
        //调用方法实现修改
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
            return Result.success();
        }else {
            return Result.error();
        }
    }
}
