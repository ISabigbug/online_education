package com.cn.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.commonutils.Result;
import com.cn.eduservice.domain.CrmBanner;
import com.cn.eduservice.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.controller
 * @Description: 后台幻灯片接口
 */
@RestController
@RequestMapping("/educms/adminbanner")
public class CrmBannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    //1.添加幻灯片
    @PostMapping("saveBanner")
    public Result saveBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);
        return Result.success();
    }

    //2.根据ID查询幻灯片
    @GetMapping("findBanner/{bannerId}")
    public Result findBanner(@PathVariable String bannerId) {
        CrmBanner crmBanner = crmBannerService.getById(bannerId);
        return Result.success().data("crmBanner", crmBanner);
    }

    //3.修改幻灯片
    @PostMapping("updateBanner")
    public Result updateBanner(@RequestBody CrmBanner CrmBanner) {
        crmBannerService.updateById(CrmBanner);
        return Result.success();
    }

    //TODO 4.分页查询幻灯片信息

    //5.根据ID删除幻灯片
    @DeleteMapping("removeBanner/{bannerId}")
    public Result removeBanner(@PathVariable String bannerId) {
        crmBannerService.removeById(bannerId);
        return Result.success();
    }
}
