package com.cn.eduservice.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.domain.vo
 * @Description:
 */
@Data
public class CommentVo{

    private String id;

    private String courseTitle;

    private String nickname;

    private String content;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

}
