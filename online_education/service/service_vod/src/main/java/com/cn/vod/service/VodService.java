package com.cn.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Project: online_education
 * @Package: com.cn.vod.service
 * @Description:
 */
public interface VodService {
    //上传视频到阿里云
    String uploadVod(MultipartFile file);
    //根据视频id删除阿里云视频
    boolean removeVod(String vodId);
    //批量删除阿里云视频
    boolean removeVodBatch(List<String> vodIdList);
    //获取播放凭证
    String getPlayAuto(String vodId);
}
