package com.atguigu.servicebase.config.exception;



import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    //统一异常处理类
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行全局异常处理");
    }

    //自定义异常处理
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R GuliException(GuliException e){
        log.error(e.getMsg());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
