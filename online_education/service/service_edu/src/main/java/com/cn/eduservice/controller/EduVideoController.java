package com.cn.eduservice.controller;

import com.cn.commonutils.Result;
import com.cn.eduservice.domain.EduVideo;
import com.cn.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.controller
 * @Description:
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    //1.添加小节
    @PostMapping("saveVideo")
    public Result saveVideo(@RequestBody EduVideo eduVideo){
        boolean res = eduVideoService.save(eduVideo);
        if (res) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    //2.删除小节
    @DeleteMapping("removeVideo/{videoId}")
    public Result removeVideo(@PathVariable String videoId){
        boolean res = eduVideoService.removeVideoAndVodById(videoId);
        if (res) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    //3.根据id查询小节
    @GetMapping("findVideoById/{videoId}")
    public Result findVideoById(@PathVariable String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        if (eduVideo != null) {
            return Result.success().data("eduVideo",eduVideo);
        }else {
            return Result.error();
        }
    }

    //4.修改小节
    @PostMapping("updateVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo){
        boolean res = eduVideoService.updateById(eduVideo);
        if (res) {
            return Result.success();
        }else {
            return Result.error();
        }
    }
}
