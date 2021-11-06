package com.atguigu.eduOrder.client;

import com.atguigu.commonutils.OrderVo.UcenterMemberOrder;
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
    //根据id获取用户信息
    @GetMapping("/eduUcenter/ucenter-member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") Long id);
}
