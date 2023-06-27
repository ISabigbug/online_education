package com.cn.eduorder.client;

import com.cn.commonutils.ordervo.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Project: online_education
 * @Package: com.cn.eduorder.client
 * @Description:
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    //3.根据课程ID获取课程信息
    @GetMapping("/eduservice/courseFront/getInfoByCid/{cid}")
    public Course getInfoByCid(@PathVariable String cid);

}
