package com.cn.eduservice.domain.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.domain.subject
 * @Description: 一级分类
 */
@Data
public class OneSubject {

    private String id;

    private String title;

    private List<TwoSubject> children;
}
