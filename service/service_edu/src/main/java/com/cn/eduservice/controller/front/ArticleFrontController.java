package com.cn.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.commonutils.Result;
import com.cn.eduservice.domain.Article;
import com.cn.eduservice.domain.vo.ArticleQuery;
import com.cn.eduservice.service.ArticleService;
import com.cn.servicebase.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.controller.front
 * @Description:
 */
@RestController
@RequestMapping("/eduservice/articleFront")
public class ArticleFrontController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisCache redisCache;
    //分页查询文章信息
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public Result pageCourseCondition(@PathVariable long current, @PathVariable long limit,
                                      @RequestBody(required = false) ArticleQuery articleQuery) {
        //创建page对象
        Page<Article> pageCourse = new Page<>(current, limit);
        //组装条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(articleQuery.getTitle()), Article::getTitle, articleQuery.getTitle())
                .like(StringUtils.isNotBlank(articleQuery.getCategoryName()), Article::getCategoryName, articleQuery.getCategoryName())
                .orderByDesc(Article::getIsTop);
        //调用方法实现条件查询分页
        articleService.page(pageCourse, wrapper);
        long total = pageCourse.getTotal();//记录数
        List<Article> records = pageCourse.getRecords();//总数据
        return Result.success().data("total", total).data("records", records);
    }

    //分页查询前端文章信息
    @PostMapping("pageArticleFront/{page}/{limit}")
    public Result pageCourseFront(@PathVariable long page, @PathVariable long limit,
                                  @RequestBody(required = false) ArticleQuery articleQuery) {
        Page<Article> pageArticle = new Page<>(page, limit);
        Map<String, Object> articleFrontList = articleService.pageArticleFront(pageArticle, articleQuery);
        return Result.success().data(articleFrontList);
    }

    //根据文章ID获取文章详细信息
    @GetMapping("getArticleDetail/{aid}")
    public Result getArticleDetail(@PathVariable("aid") Long aid){
        Article article = articleService.getById(aid);
        //从redis获得viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", aid.toString());
        article.setViewCount(viewCount.longValue());
        return Result.success().data("article",article);
    }

    //根据文章ID更新浏览量
    @PutMapping("updateViewCount/{aid}")
    public Result updateViewCount(@PathVariable("aid") Long aid) {
        //更新redis中对应id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount", aid.toString(), 1);
        return Result.success();
    }
}
