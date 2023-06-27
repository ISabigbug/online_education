package com.cn.eduservice.domain.vo;

import lombok.Data;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.domain.vo
 * @Description:
 */
@Data
public class ArticleQuery {
    //标题
    private String title;
    //状态（0已发布，1草稿）
    private String status;
}
