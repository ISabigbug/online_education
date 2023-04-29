package com.cn.educenter.controller;

import com.cn.commonutils.JwtUtils;
import com.cn.educenter.domain.UcenterMember;
import com.cn.educenter.service.UcenterMemberService;
import com.cn.educenter.utils.ConstantPropertiesUtil;
import com.cn.educenter.utils.HttpClientUtils;
import com.cn.servicebase.exception.GuliException;
import com.google.gson.Gson;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/api/ucenter/wx"})
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping({"callback"})
    public String callback(String code, String state) {
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID, ConstantPropertiesUtil.WX_OPEN_APP_SECRET, code);
        String accessTokenInfo = null;

        try {
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception var18) {
            throw new GuliException(20001, "获取access_token失败");
        }

        Gson gson = new Gson();
        HashMap map = (HashMap)gson.fromJson(accessTokenInfo, HashMap.class);
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");
        UcenterMember ucenterMember = this.ucenterMemberService.getByOpenId(openid);
        String token;
        if (ucenterMember == null) {
            token = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
            String userInfoUrl = String.format(token, accessToken, openid);
            String userInfo = null;

            try {
                userInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception var17) {
                throw new GuliException(20001, "获取用户信息失败");
            }

            HashMap userInfoMap = (HashMap)gson.fromJson(userInfo, HashMap.class);
            String nickname = (String)userInfoMap.get("nickname");
            String headimgurl = (String)userInfoMap.get("headimgurl");
            ucenterMember = new UcenterMember();
            ucenterMember.setAvatar(headimgurl);
            ucenterMember.setNickname(nickname);
            ucenterMember.setOpenid(openid);
            this.ucenterMemberService.save(ucenterMember);
        }

        token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return "redirect:http://localhost:3000?token=" + token;
    }

    @GetMapping({"wxLogin"})
    public String wxLogin() {
        String url = this.ucenterMemberService.wxLogin();
        return "redirect:" + url;
    }
}
