package com.cn.edusta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Project: online_education
 * @Package: com.cn.edusta.client
 * @Description:
 */
@FeignClient("service-ucenter")
@Component
public interface UcenterClient {

    @GetMapping("/educenter/menmber/countRegister/{day}")
    public Integer countRegister(@PathVariable("day") String day);
}
