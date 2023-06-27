package com.cn.edusms.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;

public class AliyunSmsSDKUtils {
    public AliyunSmsSDKUtils() {
    }

    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = (new Config()).setAccessKeyId(accessKeyId).setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }
}
