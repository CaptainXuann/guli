package com.atguigu.eduSta.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduSta.client.UcenterClient;
import com.atguigu.eduSta.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/eduSta/daily")
public class DailyController {
    @Autowired
    private DailyService dailyService;

    //统计某一天注册人数
    @PostMapping("/createStatisticsByDay/{day}")
    public R createStatisticsByDay(@PathVariable String day){
        dailyService.registerStatisticsByDay(day);
        return R.ok();
    }

    //图表显示，返回两部分数据，日期json数组，数量json数组
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = dailyService.getShowData(type,begin,end);
        return R.ok().data(map);
    }

}

