package com.atguigu.eduSta.service;

import com.atguigu.eduSta.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-05
 */
public interface DailyService extends IService<Daily> {

    void registerStatisticsByDay(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
