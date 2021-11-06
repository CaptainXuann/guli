package com.atguigu.eduService.client;

import com.atguigu.eduService.client.impl.OrderClinetImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author
 */
@Component
@FeignClient(value = "service-order")
public interface OrderClient {
    //根据【用户id、课程id】查询订单表中的状态
    @GetMapping("/eduOrder/order/isBuyCourse/{memberId}/{courseId}")
    public Boolean isBuyCourse(@PathVariable("memberId") String memberId, @PathVariable("courseId") String courseId);
}
