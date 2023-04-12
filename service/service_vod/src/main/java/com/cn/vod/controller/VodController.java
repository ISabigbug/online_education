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

    @PostMapping("uploadVod")
    public Result uploadVod(MultipartFile file) {
        String vodId = vodService.uploadVod(file);
        return Result.success().data("vodId", vodId);
    }

    @DeleteMapping("removeVod/{vodId}")
    public Result removeVod(@PathVariable String vodId) {
        boolean res = vodService.removeVod(vodId);
        if (res) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("removeVodBatch")
    public Result removeVodBatch(@RequestParam("vodIdList") List<String> vodIdList){
        boolean res = vodService.removeVodBatch(vodIdList);
        if (res) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

}
