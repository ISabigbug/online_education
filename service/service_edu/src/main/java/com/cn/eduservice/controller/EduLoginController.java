package com.cn.eduservice.controller;

import com.cn.commonutils.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.controller
 * @Description:
 */
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    //Login
    @PostMapping("login")
    public Result login() {
        return Result.success().data("token", "admin");
    }

    //info
    @GetMapping("info")
    public Result info() {
        return Result.success().data("roles", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
