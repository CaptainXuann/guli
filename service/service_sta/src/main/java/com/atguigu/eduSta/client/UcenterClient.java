package com.atguigu.eduSta.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //根据日期，获取那天注册人数
    @GetMapping("/eduUcenter/ucenter-member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
