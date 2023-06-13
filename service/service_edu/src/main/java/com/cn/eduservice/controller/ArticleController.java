package com.cn.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.commonutils.Result;
import com.cn.eduservice.domain.Article;
import com.cn.eduservice.domain.vo.ArticleQuery;
import com.cn.eduservice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.controller
 * @Description:
 */
@RestController
@RequestMapping("/eduservice/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    //分页查询后台文章列表
    @PostMapping("pageArticle/{page}/{limit}")
    public Result pageArticle(@PathVariable long page, @PathVariable long limit,
                                  @RequestBody(required = false) ArticleQuery articleQuery) {
        Page<Article> pageArticle = new Page<>(page, limit);
        Map<String, Object> articleList = articleService.pageArticle(pageArticle, articleQuery);
        return Result.success().data(articleList);
    }

    //根据文章ID删除文章
    @DeleteMapping("removeArticle/{aid}")
    public Result removeArticle(@PathVariable String aid) {
        articleService.removeById(aid);
        return Result.success();
    }

    //根据文章ID批量删除文章
    @DeleteMapping("removeArticleList")
    public Result removeArticleList(@RequestBody List<String> aidList) {
        articleService.removeBatchByIds(aidList);
        return Result.success();
    }

    //根据文章ID获取文章信息
    @GetMapping("findArticleById/{aid}")
    public Result findArticleById(@PathVariable String aid) {
        Article article = articleService.getById(aid);
        return Result.success().data("article",article);
    }

    //添加文章信息
    @PostMapping("saveArticle")
    public Result saveArticle(@RequestBody Article article) {
        articleService.save(article);
        return Result.success();
    }

    //修改文章信息
    @PostMapping("updateArticle")
    public Result updateArticle(@RequestBody Article article) {
        articleService.updateById(article);
        return Result.success();
    }
}
