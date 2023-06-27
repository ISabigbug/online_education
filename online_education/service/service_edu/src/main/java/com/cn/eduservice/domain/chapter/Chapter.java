package com.cn.eduservice.domain.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.domain.chapter
 * @Description:
 */
@Data
public class Chapter {

    private String id;

    private String title;

    private List<Video> children = new ArrayList<>();

}
