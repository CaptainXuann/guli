package com.atguigu.eduOrder.client;

import com.atguigu.commonutils.OrderVo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author
 */
@Component
@FeignClient("service-edu")
public interface EduClient {
    //根据课程id获取用户信息
    @GetMapping("/eduService/courseFront/getInfoById/{courseId}")
    public CourseWebVoOrder getInfoById(@PathVariable("courseId") String id);
}
