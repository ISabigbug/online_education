package com.cn.educenter.controller;

import com.cn.commonutils.JwtUtils;
import com.cn.commonutils.Result;
import com.cn.educenter.domain.UcenterMember;
import com.cn.educenter.domain.vo.RegisterVo;
import com.cn.educenter.service.UcenterMemberService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/educenter/menmber"})
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping({"login"})
    public Result loginUser(@RequestBody UcenterMember ucenterMember) {
        String token = this.ucenterMemberService.login(ucenterMember);
        return Result.success().data("token", token);
    }

    @PostMapping({"register"})
    public Result registerUser(@RequestBody RegisterVo registerVo) {
        this.ucenterMemberService.registerUser(registerVo);
        return Result.success();
    }

    @GetMapping({"findUserInfo"})
    public Result findUserInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = (UcenterMember)this.ucenterMemberService.getById(memberId);
        return Result.success().data("userInfo", member);
    }
}
