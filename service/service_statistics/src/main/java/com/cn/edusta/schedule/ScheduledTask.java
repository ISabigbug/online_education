package com.cn.edusta.schedule;

import com.cn.edusta.service.StatisticsDailyService;
import com.cn.edusta.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Project: online_education
 * @Package: com.cn.edusta.schedule
 * @Description:
 */
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //在每天凌晨一点，把前一天的数据查询添加
    @Scheduled(cron = "0 0 1 * * ? ")
    public void task() {
        statisticsDailyService.saveRegister(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
