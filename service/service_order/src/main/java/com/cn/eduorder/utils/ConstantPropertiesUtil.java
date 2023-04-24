package com.cn.eduorder.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {
    @Value("${wx.pay.appid}")
    private String appId;
    @Value("${wx.pay.partner}")
    private String partner;
    @Value("${wx.pay.partnerkey}")
    private String partnerkey;
    @Value("${wx.pay.notifyurl}")
    private String notifyurl;
    public static String WX_PAY_APP_ID;
    public static String WX_PAY_PARTNER;
    public static String WX_PAY_PARTNER_KEY;
    public static String WX_PAY_NOTIFY_URL;

    public void afterPropertiesSet() throws Exception {
        WX_PAY_APP_ID = appId;
        WX_PAY_PARTNER = partner;
        WX_PAY_PARTNER_KEY = partnerkey;
        WX_PAY_NOTIFY_URL = notifyurl;
    }
}
