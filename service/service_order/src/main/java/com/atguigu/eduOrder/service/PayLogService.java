package com.atguigu.eduOrder.service;

import com.atguigu.eduOrder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-04
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createWxQrcode(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
