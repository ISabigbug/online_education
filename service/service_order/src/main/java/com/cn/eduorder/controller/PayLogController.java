package com.cn.eduorder.controller;

import com.cn.commonutils.Result;
import com.cn.eduorder.service.PayLogService;
import com.cn.servicebase.exception.GuliException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支付日志表(PayLog)表控制层
 *
 * @author makejava
 * @since 2023-04-24 15:04:29
 */
@RestController
@RequestMapping("/eduorder/payLog")
@CrossOrigin
@MapperScan("com.cn.eduorder.mapper")
public class PayLogController{
    /**
     * 服务对象
     */
    @Autowired
    private PayLogService payLogService;

    //1.生成二维码
    @GetMapping("createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo) {
        Map<String,Object> map = payLogService.createNative(orderNo);
        return Result.success().data(map);
    }

    //2.查看订单支付状态
    @GetMapping("getPayStatus/{orderNo}")
    public Result getPayStatus(@PathVariable String orderNo) {
        Map<String,String> map = payLogService.getPayStatus(orderNo);
        if (map == null) {
            throw new GuliException(20001," 支付失败 ");
        }

        //如果返回的map不为空，通过map获取订单状态
        if (map.get("trade_state").equals("SUCCESS")) {//支付成功
            payLogService.updataPayStatus(map);
            return Result.success().message(" 支付成功 ");
        }
        return Result.error().message(" 正在支付中 ");
    }
}

