package com.cn.eduservice.client;

import com.cn.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.client.impl
 * @Description:
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public Result removeVod(String vodId) {
        return Result.error().message("删除视频出错了 熔断器...");
    }

    @Override
    public Result removeVodBatch(List<String> vodIdList) {
        return Result.error().message("删除多个视频出错了 熔断器...");
    }
}
