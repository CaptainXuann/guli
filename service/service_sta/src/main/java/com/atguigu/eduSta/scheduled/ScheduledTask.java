package com.atguigu.eduSta.scheduled;

import com.atguigu.eduSta.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author
 */
@Component
public class ScheduledTask {

    @Autowired
    private DailyService dailyService;

    //在每天凌晨1点执行方法，把前一天的数据查询进行添加
    @Scheduled(cron = "0 0 1 * * ? ")//指定cron表达式规则
    public void task02(){
        dailyService.registerStatisticsByDay(com.atguigu.staservice.sched.DateUtil.formatDate(com.atguigu.staservice.sched.DateUtil.addDays(new Date(), -1)));
    }


}
