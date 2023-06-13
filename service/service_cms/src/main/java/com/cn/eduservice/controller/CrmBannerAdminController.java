package com.cn.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.commonutils.Result;
import com.cn.eduservice.domain.CrmBanner;
import com.cn.eduservice.domain.vo.BannerQuery;
import com.cn.eduservice.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        crmBannerService.saveBanner(crmBanner);
        return Result.success();
    }

    //2.根据ID查询幻灯片
    @GetMapping("findBanner/{bannerId}")
    public Result findBanner(@PathVariable String bannerId) {
        CrmBanner crmBanner = crmBannerService.getById(bannerId);
        return Result.success().data("crmBanner", crmBanner);
    }

    //3.更新幻灯片
    @PostMapping("updateBanner")
    public Result updateBanner(@RequestBody CrmBanner CrmBanner) {
        crmBannerService.updateBanner(CrmBanner);
        return Result.success();
    }


    //分页查询后台文章列表
    @PostMapping("pageBanner/{page}/{limit}")
    public Result pageArticle(@PathVariable long page, @PathVariable long limit,
                              @RequestBody(required = false) BannerQuery bannerQuery) {
        Page<CrmBanner> pageArticle = new Page<>(page, limit);
        Map<String, Object> bannerList = crmBannerService.pageBanner(pageArticle, bannerQuery);
        return Result.success().data(bannerList);
    }

    //根据幻灯片ID批量删除幻灯片
    @DeleteMapping("removeBannerList")
    public Result removeArticleList(@RequestBody List<String> bannerList) {
        crmBannerService.removeBatchByIds(bannerList);
        return Result.success();
    }

    //5.根据ID删除幻灯片
    @DeleteMapping("removeBanner/{bannerId}")
    public Result removeBanner(@PathVariable String bannerId) {
        crmBannerService.removeById(bannerId);
        return Result.success();
    }
}
