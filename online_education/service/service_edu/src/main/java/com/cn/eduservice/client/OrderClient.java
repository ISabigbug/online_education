package com.cn.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.client
 * @Description:
 */
@FeignClient("service-order")
@Component
public interface OrderClient {

    //3.根据用户ID和课程ID判断是否已购买该课程
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{menberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("menberId") String menberId);

}
