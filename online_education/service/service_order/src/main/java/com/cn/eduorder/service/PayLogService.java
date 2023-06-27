package com.cn.eduorder.service;

import com.cn.eduorder.domain.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author Lenovo
* @description 针对表【t_pay_log(支付日志表)】的数据库操作Service
* @createDate 2023-04-24 15:03:30
*/
public interface PayLogService extends IService<PayLog> {
    //1.生成二维码
    Map<String, Object> createNative(String orderNo);

    //2.获取订单支付状态
    Map<String, String> getPayStatus(String orderNo);

    //2.查看订单支付状态
    void updataPayStatus(Map<String, String> map);
}
