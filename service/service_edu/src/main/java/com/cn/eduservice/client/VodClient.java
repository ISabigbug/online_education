package com.cn.eduservice.client;

import com.cn.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.client
 * @Description:
 */
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    //根据视频id删除阿里云视频
    @DeleteMapping("/eduvod/vod/removeVod/{vodId}")
    public Result removeVod(@PathVariable("vodId") String vodId);

    //批量删除阿里云视频
    @DeleteMapping("/eduvod/vod/removeVodBatch")
    public Result removeVodBatch(@RequestParam("vodIdList") List<String> vodIdList);
}
