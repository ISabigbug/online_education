package com.cn.eduservice.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Project: online_education
 * @Package: com.cn.eduservice.domain.excel
 * @Description:
 */
@Data
public class SubjectData {

    @ExcelProperty(index = 0)
    private String OneSubjectName;

    @ExcelProperty(index = 1)
    private String TwoSubjectName;
}
