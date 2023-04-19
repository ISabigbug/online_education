package com.cn.edusms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import com.cn.edusms.service.SmsService;
import com.cn.edusms.utils.AliyunSmsSDKUtils;
import com.cn.edusms.utils.ConstantPropertiesUtils;
import java.util.HashMap;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {
    public SmsServiceImpl() {
    }

    public boolean send(String phone, String templateCode, HashMap<String, Object> param) {
        try {
            Client client = AliyunSmsSDKUtils.createClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            SendSmsRequest sendSmsRequest = (new SendSmsRequest()).setSignName("阿里云短信测试").setTemplateCode(templateCode).setPhoneNumbers(phone).setTemplateParam(JSONObject.toJSONString(param));
            RuntimeOptions runtime = new RuntimeOptions();
            SendSmsResponse response = client.sendSmsWithOptions(sendSmsRequest, runtime);
            return response.getBody().getCode().equals("OK");
        } catch (Exception var8) {
            var8.printStackTrace();
            return false;
        }
    }
}
