package com.cn.edusta.controller;


import com.cn.commonutils.Result;
import com.cn.edusta.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 网站统计日数据(StatisticsDaily)表控制层
 *
 * @author makejava
 * @since 2023-04-25 19:48:55
 */
@RestController
@RequestMapping("/edusta/sta")
public class StatisticsDailyController{
    /**
     * 服务对象
     */
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //1.统计某天的注册人数并加入到数据库中
    @GetMapping("saveRegister/{day}")
    public Result saveRegister(@PathVariable String day) {
        statisticsDailyService.saveRegister(day);
        return Result.success();
    }

    //2.生成图表
    @GetMapping("showChart/{type}/{begin}/{end}")
    public Result showChart(@PathVariable String type,@PathVariable String begin,@PathVariable String end) {
        Map<String,Object> map = statisticsDailyService.showChart(type,begin,end);
        return Result.success().data(map);
    }
}

