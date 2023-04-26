package com.cn.edusta.service;

import com.cn.edusta.domain.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author Lenovo
* @description 针对表【statistics_daily(网站统计日数据)】的数据库操作Service
* @createDate 2023-04-25 19:48:25
*/
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //1.统计某天的注册人数并加入到数据库中
    void saveRegister(String day);

    //2.生成图表
    Map<String, Object> showChart(String type, String begin, String end);
}
