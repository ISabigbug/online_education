package com.cn.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.eduservice.domain.EduCourse;
import com.cn.eduservice.domain.EduCourseDescription;
import com.cn.eduservice.domain.vo.CoursePublishVo;
import com.cn.eduservice.domain.vo.CourseVo;
import com.cn.eduservice.service.EduChapterService;
import com.cn.eduservice.service.EduCourseDescriptionService;
import com.cn.eduservice.service.EduCourseService;
import com.cn.eduservice.mapper.EduCourseMapper;
import com.cn.eduservice.service.EduVideoService;
import com.cn.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Lenovo
 * @description 针对表【edu_course(课程)】的数据库操作Service实现
 * @createDate 2023-03-19 15:20:54
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse>
        implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Override
    public String saveCourseInfo(CourseVo courseVo) {
        //向课程表添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new GuliException(20001, "添加课程信息失败");
        } else {
            //获取课程id
            String cid = eduCourse.getId();

            //向课程简介表添加课程简介
            EduCourseDescription eduCourseDescription = new EduCourseDescription();
            eduCourseDescription.setDescription(courseVo.getDescription());
            eduCourseDescription.setId(cid);
            eduCourseDescriptionService.save(eduCourseDescription);
            return cid;
        }
    }

    @Override
    public CourseVo findCourseInfo(String cid) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(cid);
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(eduCourse, courseVo);

        //查询简介表，获取课程简介
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(cid);
        courseVo.setDescription(eduCourseDescription.getDescription());
        return courseVo;
    }

    @Override
    public void updateConseInfo(CourseVo courseVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);

        if (update == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }

        //修改课程简历表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseVo.getId());
        eduCourseDescription.setDescription(courseVo.getDescription());
        boolean res = eduCourseDescriptionService.updateById(eduCourseDescription);
        if (!res) {
            throw new GuliException(20001, "修改课程简历失败");
        }
    }

    @Override
    public CoursePublishVo findCoursePublishVo(String cid) {
        CoursePublishVo coursePublishVo = baseMapper.findCoursePublishVo(cid);
        return coursePublishVo;
    }

    @Override
    public boolean updateStatusById(String status, String cid) {
        int i = baseMapper.updateStatusById("Normal", cid);
        return i > 0;
    }

    @Override
    public void removeCourseByCid(String cid) {
        //删除小节
        eduVideoService.deleteVideoByCid(cid);

        //删除章节
        eduChapterService.deleteChapterByCourseId(cid);

        //删除简介
        eduCourseDescriptionService.deleteDescByCourseId(cid);


        //删除课程本身
        int deleteCourse = baseMapper.deleteById(cid);
        if (deleteCourse == 0) {
            throw new GuliException(20001, "删除课程失败");
        }
    }


}




