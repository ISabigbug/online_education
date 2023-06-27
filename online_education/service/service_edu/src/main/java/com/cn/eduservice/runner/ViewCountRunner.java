package com.cn.eduservice.runner;

import com.cn.eduservice.domain.Article;
import com.cn.eduservice.mapper.ArticleMapper;
import com.cn.servicebase.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.utils
 * @Description:
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;

    @Override
    //在启动服务时
    public void run(String... args) throws Exception {
        //查询博客信息
        List<Article> articles = articleMapper.selectList(null);
        Map<String , Integer> ViewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        //存储道redis
        redisCache.setCacheMap("article:viewCount",ViewCountMap);
    }
}
