package com.cn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.eduservice.domain.Article;
import com.cn.eduservice.domain.vo.ArticleFrontQuery;
import com.cn.eduservice.domain.vo.ArticleQuery;
import com.cn.eduservice.mapper.ArticleMapper;
import com.cn.eduservice.service.ArticleService;
import com.cn.servicebase.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


/**
 * @Project: online_education
 * @Package: com.cn.eduservice.service.impl
 * @Description:
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private RedisCache redisCache;
    @Override
    public Map<String, Object> pageArticleFront(Page<Article> pageArticle, ArticleFrontQuery articleQuery) {
        //根据分页条件查询
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(articleQuery.getTitle()), Article::getTitle, articleQuery.getTitle())
                .like(StringUtils.isNotBlank(articleQuery.getCategoryName()), Article::getCategoryName, articleQuery.getCategoryName())
                .orderByDesc(Article::getIsTop);
        baseMapper.selectPage(pageArticle, lambdaQueryWrapper);
        List<Article> articleList = pageArticle.getRecords();
        for (Article article : articleList) {
            //从redis获得viewCount
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }
        //将查询结果封装到map中
        long current = pageArticle.getCurrent();
        long pages = pageArticle.getPages();
        long size = pageArticle.getSize();
        long total = pageArticle.getTotal();
        boolean hasNext = pageArticle.hasNext();
        boolean hasPrevious = pageArticle.hasPrevious();
        Map<String, Object> map = new HashMap();
        map.put("records", articleList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public Map<String, Object> pageArticle(Page<Article> pageArticle, ArticleQuery articleQuery) {
        //根据分页条件查询
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(articleQuery.getTitle()), Article::getTitle, articleQuery.getTitle())
                .eq(StringUtils.isNotBlank(articleQuery.getStatus()), Article::getStatus, articleQuery.getStatus())
                .orderByDesc(Article::getIsTop);
        baseMapper.selectPage(pageArticle, lambdaQueryWrapper);
        //将查询结果封装到map中
        List<Article> articleList = pageArticle.getRecords();
        long current = pageArticle.getCurrent();
        long pages = pageArticle.getPages();
        long size = pageArticle.getSize();
        long total = pageArticle.getTotal();
        boolean hasNext = pageArticle.hasNext();
        boolean hasPrevious = pageArticle.hasPrevious();
        Map<String, Object> map = new HashMap();
        map.put("records", articleList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
