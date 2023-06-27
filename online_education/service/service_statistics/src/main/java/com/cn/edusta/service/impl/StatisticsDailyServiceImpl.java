package com.cn.edusta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.edusta.client.UcenterClient;
import com.cn.edusta.domain.StatisticsDaily;
import com.cn.edusta.service.StatisticsDailyService;
import com.cn.edusta.mapper.StatisticsDailyMapper;
import org.apache.commons.lang3.RandomUtils;
import org.apache.ibatis.annotations.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Lenovo
 * @description 针对表【statistics_daily(网站统计日数据)】的数据库操作Service实现
 * @createDate 2023-04-25 19:48:25
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily>
        implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void saveRegister(String day) {

        //删除相同日期数据
        LambdaQueryWrapper<StatisticsDaily> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StatisticsDaily::getDateCalculated, day);
        baseMapper.delete(lambdaQueryWrapper);

        //获取某天的注册人数
        Integer registerNum = ucenterClient.countRegister(day);

        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> showChart(String type, String begin, String end) {
        LambdaQueryWrapper<StatisticsDaily> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.between(StatisticsDaily::getDateCalculated, begin, end);

        List<StatisticsDaily> dailyList = baseMapper.selectList(lambdaQueryWrapper);

        //分别存入DATE数据和统计数目数据
        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();

        //遍历dailyList获取数据
        for (StatisticsDaily daily : dailyList) {
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    numList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    numList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        //最终存储DATE数据和统计数目数据
        Map<String, Object> map = new HashMap<>();
        map.put("dateList",dateList);
        map.put("numList",numList);

        return map;
    }
}




