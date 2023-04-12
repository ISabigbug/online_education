package com.cn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.commonutils.Result;
import com.cn.eduservice.client.VodClient;
import com.cn.eduservice.domain.EduVideo;
import com.cn.eduservice.domain.chapter.Video;
import com.cn.eduservice.service.EduVideoService;
import com.cn.eduservice.mapper.EduVideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lenovo
 * @description 针对表【edu_video(课程视频)】的数据库操作Service实现
 * @createDate 2023-03-19 15:35:50
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo>
        implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void deleteVideoByCourseId(String cid) {
        int i = baseMapper.deleteByCourseId(cid);
    }

    @Override
    public boolean removeVideoAndVodById(String videoId) {
        EduVideo eduVideo = baseMapper.selectById(videoId);
        if (eduVideo.getVideoSourceId() != null) {
            vodClient.removeVod(eduVideo.getVideoSourceId());
        }
        int i = baseMapper.deleteById(videoId);
        return i > 0;
    }

    @Override
    public void deleteVideoByCid(String cid) {
        //通过cid查询视频id
        LambdaQueryWrapper<EduVideo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(EduVideo::getCourseId, cid);
        List<EduVideo> eduVideos = baseMapper.selectList(lambdaQueryWrapper);
        //得到所有视频列表的云端原始视频id
        List<String> videoSourceIdList = new ArrayList<>();
        for (int i = 0; i < eduVideos.size(); i++) {
            EduVideo eduVideo = eduVideos.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if (StringUtils.isNotBlank(videoSourceId)) {
                videoSourceIdList.add(videoSourceId);
            }
        }
        //调用vod服务删除远程视频
        if (videoSourceIdList.size() > 0) {
            vodClient.removeVodBatch(videoSourceIdList);
        }
        //删除小节
        baseMapper.deleteByCourseId(cid);
    }
}




