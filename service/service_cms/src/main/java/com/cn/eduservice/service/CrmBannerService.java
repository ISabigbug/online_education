package com.cn.eduservice.service;

import com.cn.eduservice.domain.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【crm_banner(首页banner表)】的数据库操作Service
* @createDate 2023-04-12 21:05:38
*/
public interface CrmBannerService extends IService<CrmBanner> {

    //1.查询所有幻灯片
    List<CrmBanner> findAllBanner();
}
