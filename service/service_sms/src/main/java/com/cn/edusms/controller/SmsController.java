package com.cn.edusms.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cn.commonutils.Result;
import com.cn.edusms.service.SmsService;
import com.cn.edusms.utils.ConstantPropertiesUtils;
import com.cn.edusms.utils.RandomUtil;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/edusms/sms"})
public class SmsController {
    @Autowired
    private SmsService smsService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping({"send/{phone}"})
    public Result send(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if (StringUtils.isNotBlank(code)) {
            return Result.success();
        } else {
            code = RandomUtil.getSixBitRandom();
            HashMap<String, Object> param = new HashMap();
            param.put("code", code);
            boolean isSend = this.smsService.send(phone, ConstantPropertiesUtils.VERIFY_SMS, param);
            if (isSend) {
                this.redisTemplate.opsForValue().set(phone, code, 5L, TimeUnit.MINUTES);
                return Result.success();
            } else {
                return Result.error().message(" 发送信息失败，请重新尝试! ");
            }
        }
    }
}
