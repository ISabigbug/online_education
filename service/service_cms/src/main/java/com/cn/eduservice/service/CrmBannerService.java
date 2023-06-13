package com.cn.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.eduservice.domain.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.eduservice.domain.vo.BannerQuery;

import java.util.List;
import java.util.Map;

/**
* @author Lenovo
* @description 针对表【crm_banner(首页banner表)】的数据库操作Service
* @createDate 2023-04-12 21:05:38
*/
public interface CrmBannerService extends IService<CrmBanner> {

    //1.查询所有幻灯片
    List<CrmBanner> findAllBanner();

    //2.后台分页查询所有幻灯片
    Map<String, Object> pageBanner(Page<CrmBanner> pageArticle, BannerQuery bannerQuery);

    //3.添加幻灯片
    void saveBanner(CrmBanner crmBanner);

    //4.更新幻灯片
    void updateBanner(CrmBanner crmBanner);
}
