package com.atguigu.eduService.client;

import com.atguigu.commonutils.R;
import com.atguigu.eduService.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author
 */
@FeignClient(value = "service-ucenter")
@Component
public interface UcenterClient {

    //根据id获取用户信息
    @GetMapping("/eduUcenter/ucenter-member/getMemberInfoById/{id}")
    R getMemberInfoById(@PathVariable("id") String id);

}
