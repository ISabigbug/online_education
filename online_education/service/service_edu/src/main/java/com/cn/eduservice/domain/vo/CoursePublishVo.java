package com.cn.eduservice.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.domain.vo
 * @Description:
 */
@Data
public class CoursePublishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
