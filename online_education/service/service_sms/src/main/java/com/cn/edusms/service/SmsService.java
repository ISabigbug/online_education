package com.cn.edusms.service;

import java.util.HashMap;

public interface SmsService {
    boolean send(String phone, String templateCode, HashMap<String, Object> param);
}
