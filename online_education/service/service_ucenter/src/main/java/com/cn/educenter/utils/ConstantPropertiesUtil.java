package com.cn.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {
    @Value("${wx.open.app_id}")
    private String appId;
    @Value("${wx.open.app_secret}")
    private String appSecret;
    @Value("${wx.open.redirect_url}")
    private String redirectUrl;
    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;

    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = this.appId;
        WX_OPEN_APP_SECRET = this.appSecret;
        WX_OPEN_REDIRECT_URL = this.redirectUrl;
    }
}
