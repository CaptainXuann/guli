package com.atguigu.eduOrder.service;

import com.atguigu.eduOrder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-04
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberIdByJwtToken);

}
