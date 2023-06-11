package com.cn.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.commonutils.Result;
import com.cn.eduservice.domain.Article;
import com.cn.eduservice.domain.EduCourse;
import com.cn.eduservice.domain.vo.ArticleQuery;

import java.util.Map;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.service
 * @Description:
 */
public interface ArticleService extends IService<Article> {
    //1.前台分页查询文章
    Map<String, Object> pageArticleFront(Page<Article> pageArticle, ArticleQuery articleQuery);

}
