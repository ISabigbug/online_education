package com.cn.eduservice.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.domain.vo
 * @Description:
 */
@Data
public class CourseQuery {
    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;

    @ApiModelProperty(value = "一级分类，模糊查询")
    private String subjectParentId;

    @ApiModelProperty(value = "二级分类，模糊查询")
    private String subjectId;

}
