package com.atguigu.eduOrder.service.impl;

import com.atguigu.commonutils.OrderVo.CourseWebVoOrder;
import com.atguigu.commonutils.OrderVo.UcenterMemberOrder;
import com.atguigu.eduOrder.client.EduClient;
import com.atguigu.eduOrder.client.UcenterClient;
import com.atguigu.eduOrder.entity.Order;
import com.atguigu.eduOrder.mapper.OrderMapper;
import com.atguigu.eduOrder.service.OrderService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-04
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {
        //通过远程调用获取用户信息
        CourseWebVoOrder courseInfo = eduClient.getInfoById(courseId);
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(Long.valueOf(memberIdByJwtToken));
        Order order = new Order();
        order.setOrderNo(IdWorker.getIdStr());
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setMemberId(memberIdByJwtToken);
        order.setCourseCover(courseInfo.getCover());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setTotalFee(courseInfo.getPrice());
        order.setStatus(0);//支付状态：（ 0：已支付，1：未支付 ）
        order.setPayType(1);//支付类型： 1：微信 ， 2：支付宝
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
