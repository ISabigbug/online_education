package com.cn.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.eduservice.domain.Article;
import com.cn.eduservice.domain.vo.ArticleFrontQuery;
import com.cn.eduservice.domain.vo.ArticleQuery;

import java.util.Map;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.service
 * @Description:
 */
public interface ArticleService extends IService<Article> {
    //前台分页查询文章
    Map<String, Object> pageArticleFront(Page<Article> pageArticle, ArticleFrontQuery articleQuery);

    //后台分页查询文章
    Map<String, Object> pageArticle(Page<Article> pageArticle, ArticleQuery articleQuery);
}
