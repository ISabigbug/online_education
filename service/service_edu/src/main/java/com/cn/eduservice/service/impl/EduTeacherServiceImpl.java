package com.cn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.eduservice.domain.EduTeacher;
import com.cn.eduservice.service.EduTeacherService;
import com.cn.eduservice.mapper.EduTeacherMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lenovo
 * @description 针对表【edu_teacher(讲师)】的数据库操作Service实现
 * @createDate 2023-02-27 11:01:50
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher>
        implements EduTeacherService {
    @Cacheable(key = "'findTeacherList'", value = {"teacherList"})
    public List<EduTeacher> findFourTeacher() {
        LambdaQueryWrapper<EduTeacher> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(EduTeacher::getId);
        lambdaQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = baseMapper.selectList(lambdaQueryWrapper);
        return teacherList;
    }

    public Map<String, Object> pageTeacherFront(Page<EduTeacher> pageTeacher) {
        LambdaQueryWrapper<EduTeacher> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(EduTeacher::getId);
        baseMapper.selectPage(pageTeacher, lambdaQueryWrapper);
        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();
        Map<String, Object> map = new HashMap();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}




