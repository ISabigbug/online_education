package com.cn.service_edu;

import com.cn.eduservice.EduApplication;
import com.cn.eduservice.controller.EduCourseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Project: online_education
 * @Package: com.cn.service_edu
 * @Description:
 */
@SpringBootTest(classes = EduApplication.class)
public class eduTest {
    @Autowired
    private EduCourseController eduCourseController;
}
