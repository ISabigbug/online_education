package com.cn.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.eduservice.domain.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.mapper
 * @Description:
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
