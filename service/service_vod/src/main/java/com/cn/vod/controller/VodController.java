package com.cn.vod.controller;

import com.cn.commonutils.Result;
import com.cn.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Project: online_education
 * @Package: com.cn.vod.controller
 * @Description:
 */
@RestController
@RequestMapping("/eduvod/vod")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("uploadVod")
    public Result uploadVod(MultipartFile file) {
        String vodId = vodService.uploadVod(file);
        return Result.success().data("vodId", vodId);
    }

    //根据视频id删除阿里云视频
    @DeleteMapping("removeVod/{vodId}")
    public Result removeVod(@PathVariable String vodId) {
        boolean res = vodService.removeVod(vodId);
        if (res) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    //批量删除阿里云视频
    @DeleteMapping("removeVodBatch")
    public Result removeVodBatch(@RequestParam("vodIdList") List<String> vodIdList){
        boolean res = vodService.removeVodBatch(vodIdList);
        if (res) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    //获取视频播放凭证
    @GetMapping("getPlayAuth/{vodId}")
    public Result getPlayAuth(@PathVariable String vodId) {
        String playAuth = vodService.getPlayAuto(vodId);
        return Result.success().data("playAuth",playAuth);
    }


}
