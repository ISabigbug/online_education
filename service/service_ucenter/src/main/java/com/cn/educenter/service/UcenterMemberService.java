package com.cn.educenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.commonutils.ordervo.Menber;
import com.cn.educenter.domain.UcenterMember;
import com.cn.educenter.domain.vo.RegisterVo;

public interface UcenterMemberService extends IService<UcenterMember> {
    String login(UcenterMember ucenterMember);

    void registerUser(RegisterVo registerVo);

    String wxLogin();

    UcenterMember getByOpenId(String openid);

    Menber getUserInfoById(String id);
}
