package com.cn.servicebase.exception;

import com.cn.commonutils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Project: online_education
 * @Package: com.cn.servicebase.exception
 * @Description:
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionhandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("出现未知异常，请联系管理员处理");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        //将错误信息存入日志
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error().message("执行了ArithmeticException异常处理");
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public Result error(GuliException e){
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }
}
