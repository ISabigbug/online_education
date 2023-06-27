package com.cn.eduorder.service;

import com.cn.eduorder.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Lenovo
* @description 针对表【t_order(订单)】的数据库操作Service
* @createDate 2023-04-24 15:00:05
*/
public interface OrderService extends IService<Order> {

    //1.根据课程ID和用户ID生成订单
    String saveOrderByCourseId(String courseId, String memberId);

    //2.根据订单号查询订单信息
    Order getOrderInfo(String orderNo);

    //3.根据用户ID和课程ID判断是否已购买该课程
    boolean isBuyCourse(String courseId, String menberId);
}
