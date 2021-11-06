package com.atguigu.eduOrder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduOrder.entity.Order;
import com.atguigu.eduOrder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-04
 */
@RestController
@RequestMapping("/eduOrder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //生成订单的方法
    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        String orderNo=orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderNo", orderNo);
    }

    //根据订单号查询订单信息
    @GetMapping("/getOrderInfoById/{orderNo}")
    public R getOrderInfoById(@PathVariable String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    //根据【用户id、课程id】查询订单表中的状态
    @GetMapping("/isBuyCourse/{memberId}/{courseId}")
    public Boolean isBuyCourse(@PathVariable String memberId,@PathVariable String courseId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId)
                .eq("member_id",memberId)
                .eq("status",1); //支付状态 【1】代表已支付
        int result = orderService.count(wrapper);
        if (result>0){//已支付
            return true;
        }else {
            return false;
        }
    }

}

