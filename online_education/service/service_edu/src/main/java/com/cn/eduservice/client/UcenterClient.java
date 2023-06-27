package com.cn.eduservice.client;

import com.cn.commonutils.ordervo.Menber;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.client
 * @Description:
 */
@FeignClient("service-ucenter")
@Component
public interface UcenterClient {
    //根据用户ID获取用户信息
    @GetMapping("/educenter/menmber/getInfoById/{id}")
    public Menber getInfoById(@PathVariable("id") String id);
}
