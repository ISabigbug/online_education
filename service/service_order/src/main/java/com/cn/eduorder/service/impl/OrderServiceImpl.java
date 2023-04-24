package com.cn.eduorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.commonutils.ordervo.Course;
import com.cn.commonutils.ordervo.Menber;
import com.cn.eduorder.client.EduClient;
import com.cn.eduorder.client.UcenterClient;
import com.cn.eduorder.domain.Order;
import com.cn.eduorder.service.OrderService;
import com.cn.eduorder.mapper.OrderMapper;
import com.cn.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lenovo
 * @description 针对表【t_order(订单)】的数据库操作Service实现
 * @createDate 2023-04-24 15:00:05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String saveOrderByCourseId(String courseId, String memberId) {
        //根据课程ID获取课程信息
        Course course = eduClient.getInfoByCid(courseId);

        //根据用户ID获取用户信息
        Menber menber = ucenterClient.getInfoById(memberId);

        //创建订单
        Order order = new Order();
        //利用工具类生成订单号并存入数据
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(course.getTitle());
        order.setCourseCover(course.getCover());
        order.setTeacherName(course.getTeacherName());
        order.setTotalFee(course.getPrice());
        order.setMemberId(memberId);
        order.setMobile(menber.getMobile());
        order.setNickname(menber.getNickname());
        order.setStatus(0);
        order.setPayType(1);

        //存入
        baseMapper.insert(order);

        return order.getOrderNo();
    }

    @Override
    public Order getOrderInfo(String orderNo) {
        Order order = baseMapper.selectOneByOrderNo(orderNo);
        return order;
    }
}




