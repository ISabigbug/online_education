package com.cn.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.cn.servicebase.exception.GuliException;
import com.cn.vod.service.VodService;
import com.cn.vod.utils.AliyunVodSDKUtils;
import com.cn.vod.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * @Project: online_education
 * @Package: com.cn.vod.service.impl
 * @Description:
 */
@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVod(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename;
            //去掉后缀
//            String title = originalFilename.substring(0,originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET, title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" +
                        response.getCode() + ", message：" + response.getMessage();
                if (StringUtils.isEmpty(videoId)) {
                    throw new GuliException(20001, errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }
    }

    @Override
    public boolean removeVod(String vodId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantPropertiesUtils.ACCESS_KEY_ID,
                    ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(vodId);
            client.getAcsResponse(request);
            return true;
        } catch (Exception e) {
            throw new GuliException(20001, "视频删除失败");
        }
    }

    @Override
    public boolean removeVodBatch(List<String> vodIdList) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantPropertiesUtils.ACCESS_KEY_ID,
                    ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String vodIds = org.apache.commons.lang.StringUtils.join(vodIdList, ",");
            request.setVideoIds(vodIds);
            client.getAcsResponse(request);
            return true;
        } catch (Exception e) {
            throw new GuliException(20001, "视频删除失败");
        }
    }

    @Override
    public String getPlayAuto(String vodId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantPropertiesUtils.ACCESS_KEY_ID,
                    ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(vodId);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return playAuth;
        } catch (Exception e) {
            throw new GuliException(20001, "获取播放凭证失败");
        }
    }
}
