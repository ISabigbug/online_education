package com.cn.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Project: online_education
 * @Package: com.cn.servicebase.exception
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException {
    private Integer code;//状态码
    private String msg;//输出信息
}
