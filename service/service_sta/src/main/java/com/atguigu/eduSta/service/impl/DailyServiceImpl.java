package com.atguigu.eduSta.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduSta.client.UcenterClient;
import com.atguigu.eduSta.entity.Daily;
import com.atguigu.eduSta.mapper.DailyMapper;
import com.atguigu.eduSta.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-05
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerStatisticsByDay(String day) {
        R r = ucenterClient.countRegister(day);
        Integer count = (Integer) r.getData().get("count");
        //其他的数据类似，也是通过远程调用，获取数据返回即可，下面使用随机数模拟

        //查询是否已经添加过
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        Daily isInsert = baseMapper.selectOne(wrapper);
        if(!StringUtils.isEmpty(isInsert)){
            isInsert.setRegisterNum(count);
            baseMapper.updateById(isInsert);
        }else {
            Daily daily = new Daily();
            //把获取到的数据
            daily.setRegisterNum(count);//注册人数
            daily.setCourseNum(RandomUtils.nextInt(200, 300));
            daily.setLoginNum(RandomUtils.nextInt(200,300));//登录数
            daily.setVideoViewNum(RandomUtils.nextInt(200,300));//视频流量数
            daily.setDateCalculated(day);//统计日期

            //添加到数据库中
            baseMapper.insert(daily);
        }

    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated",type);
        wrapper.between("date_calculated", begin, end);
        List<Daily> dailyList = baseMapper.selectList(wrapper);
        //前端要求数组json结果，对应后端为List集合
        //创建两个list集合，一个放日期X轴，一个放数量Y轴
        List<String> xlist = new ArrayList<>();
        List<Integer> ylist = new ArrayList<>();
        for (Daily daily : dailyList) {
            xlist.add(daily.getDateCalculated());
            //判断查询的哪个字段
            if ("register_num".equals(type)){
                ylist.add(daily.getRegisterNum());
            }
            if ("login_num".equals(type)){
                ylist.add(daily.getLoginNum());
            }
            if ("video_view_num".equals(type)){
                ylist.add(daily.getVideoViewNum());
            }
            if ("course_num".equals(type)){
                ylist.add(daily.getCourseNum());
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("xlist",xlist);
        map.put("ylist",ylist);
        return map;

    }
}
