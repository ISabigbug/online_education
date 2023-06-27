package com.cn.educenter.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Project: online_education
 * @Package: com.cn.educenter.domain.vo
 * @Description:
 */
@Data
public class RegisterVo {
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("验证码")
    private String code;
}
