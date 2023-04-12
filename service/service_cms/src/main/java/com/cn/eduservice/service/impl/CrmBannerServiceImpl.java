package com.cn.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.eduservice.domain.CrmBanner;
import com.cn.eduservice.service.CrmBannerService;
import com.cn.eduservice.mapper.CrmBannerMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【crm_banner(首页banner表)】的数据库操作Service实现
* @createDate 2023-04-12 21:05:38
*/
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner>
    implements CrmBannerService{

    @Override
    public List<CrmBanner> findAllBanner() {
        List<CrmBanner> bannerList = baseMapper.selectList(null);
        return bannerList;
    }
}




