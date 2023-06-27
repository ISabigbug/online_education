package com.cn.eduservice.schedule;

import com.cn.eduservice.domain.Article;
import com.cn.eduservice.service.ArticleService;
import com.cn.servicebase.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.schedule
 * @Description:
 */
@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0/10 * * * ?" )
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewCount");

        List<Article> articleList = cacheMap.entrySet()
                .stream()
                .map(stringIntegerEntry -> new Article(stringIntegerEntry.getKey(), stringIntegerEntry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articleList);
    }
}
