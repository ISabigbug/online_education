package com.cn.eduservice.controller;

import com.cn.commonutils.Result;
import com.cn.eduservice.domain.CrmBanner;
import com.cn.eduservice.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.controller
 * @Description: 前台幻灯片接口
 */
@RestController
@RequestMapping("/educms/frontbanner")
public class CrmBannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("findAllBanner")
    public Result findAllBanner() {
        List<CrmBanner> bannerList = crmBannerService.findAllBanner();
        return Result.success().data("bannerList",bannerList);
    }
}
