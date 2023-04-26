package com.cn.eduorder.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cn.commonutils.JwtUtils;
import com.cn.commonutils.Result;
import com.cn.eduorder.domain.Order;
import com.cn.eduorder.service.OrderService;
import com.cn.servicebase.exception.GuliException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 订单(Order)表控制层
 *
 * @author makejava
 * @since 2023-04-24 15:01:31
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
@MapperScan("com.cn.eduorder.mapper")
public class OrderController{
    /**
     * 服务对象
     */
    @Autowired
    private OrderService orderService;

    //1.根据课程ID和用户ID生成订单
    @GetMapping("saveOrderByCourseId/{courseId}")
    public Result saveOrderByCourseId(@PathVariable String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.saveOrderByCourseId(courseId,memberId);
        return Result.success().data("orderNo",orderNo);
    }

    //2.根据订单号查询订单信息
    @GetMapping("getOrderInfo/{orderNo}")
    public Result getOrderInfo(@PathVariable String orderNo) {
        Order order = orderService.getOrderInfo(orderNo);
        return Result.success().data("order",order);
    }

    //3.根据用户ID和课程ID判断是否已购买该课程
    @GetMapping("isBuyCourse/{courseId}/{menberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String menberId) {
        boolean res = orderService.isBuyCourse(courseId,menberId);
        return res;
    }
}

