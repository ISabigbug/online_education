package com.cn.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Project: online_education
 * @Package: com.cn.oss.service
 * @Description:
 */
public interface OssService {
    String uploadOssFileAvaTar(MultipartFile file);
}
