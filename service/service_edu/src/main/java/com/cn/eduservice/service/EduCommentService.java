package com.cn.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.eduservice.domain.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
* @author Lenovo
* @description 针对表【edu_comment(评论)】的数据库操作Service
* @createDate 2023-04-23 17:24:12
*/
public interface EduCommentService extends IService<EduComment> {

    //1.分页查询所有评论
    Map<String, Object> pageCourseFront(Page<EduComment> pageComment,String courseId);

    //2.将评论信息存入数据库
    void saveComment(EduComment eduComment, HttpServletRequest request);

    //3.根据课程id查询评论数量
    Long commentNum(String cid);
}
