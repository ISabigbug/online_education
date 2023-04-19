package com.cn.educenter.mapper;

import com.cn.educenter.domain.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Lenovo
* @description 针对表【ucenter_member(会员表)】的数据库操作Mapper
* @createDate 2023-04-19 22:59:09
* @Entity com.cn.educenter.domain.UcenterMember
*/
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    UcenterMember selectOneByOpenid(@Param("openid") String openid);
}




