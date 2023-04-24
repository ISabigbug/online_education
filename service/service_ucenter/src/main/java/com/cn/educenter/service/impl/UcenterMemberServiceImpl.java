package com.cn.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.commonutils.JwtUtils;
import com.cn.commonutils.MD5;
import com.cn.commonutils.ordervo.Menber;
import com.cn.educenter.domain.UcenterMember;
import com.cn.educenter.domain.vo.RegisterVo;
import com.cn.educenter.mapper.UcenterMemberMapper;
import com.cn.educenter.service.UcenterMemberService;
import com.cn.educenter.utils.ConstantPropertiesUtil;
import com.cn.servicebase.exception.GuliException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        if (!StringUtils.isBlank(mobile) && !StringUtils.isBlank(password)) {
            LambdaQueryWrapper<UcenterMember> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(UcenterMember::getMobile, mobile);
            UcenterMember member = baseMapper.selectOne(lambdaQueryWrapper);
            if (member == null) {
                throw new GuliException(20001, " 该手机号码输入有误或未注册，请重新输入！");
            } else if (!MD5.encrypt(password).equals(member.getPassword())) {
                throw new GuliException(20001, " 密码有误，请重新输入！");
            } else {
                String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
                return token;
            }
        } else {
            throw new GuliException(20001, " 手机号码密码不能为空！");
        }
    }

    public void registerUser(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        if (!StringUtils.isBlank(code) && !StringUtils.isBlank(nickname) && !StringUtils.isBlank(mobile) && !StringUtils.isBlank(password)) {
            String redisCode = redisTemplate.opsForValue().get(mobile);
            if (!code.equals(redisCode)) {
                throw new GuliException(20001, " 验证码有误！");
            } else {
                LambdaQueryWrapper<UcenterMember> lambdaQueryWrapper = new LambdaQueryWrapper();
                lambdaQueryWrapper.eq(UcenterMember::getMobile, mobile);
                Long count = baseMapper.selectCount(lambdaQueryWrapper);
                if (count > 0L) {
                    throw new GuliException(20001, " 该手机号码已注册！");
                } else {
                    UcenterMember ucenterMember = new UcenterMember();
                    ucenterMember.setMobile(mobile);
                    ucenterMember.setNickname(nickname);
                    ucenterMember.setPassword(MD5.encrypt(password));
                    ucenterMember.setIsDisabled(0);
                    ucenterMember.setAvatar("fuzhou.aliyuncs.com/2023/03/14e63cd250e4044a36b5a47ba6a1a64f5afile.png");
                    baseMapper.insert(ucenterMember);
                }
            }
        } else {
            throw new GuliException(20001, " 输入的值不能为空！");
        }
    }

    public String wxLogin() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;

        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException var4) {
            throw new GuliException(20001, " 二维码生成失败 ");
        }

        String url = String.format(baseUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID, redirectUrl, "Chasel");
        return url;
    }

    public UcenterMember getByOpenId(String openid) {
        UcenterMember ucenterMember = baseMapper.selectOneByOpenid(openid);
        return ucenterMember;
    }

    @Override
    public Menber getUserInfoById(String id) {
        UcenterMember ucenterMember = baseMapper.selectById(id);
        Menber menber = new Menber();
        BeanUtils.copyProperties(ucenterMember, menber);
        return menber;
    }
}
