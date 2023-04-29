package com.cn.oss.controller;

import com.cn.commonutils.Result;
import com.cn.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Project: online_education
 * @Package: com.cn.oss.controller
 * @Description:
 */
@RestController
@RequestMapping("/eduoss/file")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public Result uploadOssFileAvaTar(MultipartFile file){
        String url = ossService.uploadOssFileAvaTar(file);
        return Result.success().data("url",url);
    }
}
