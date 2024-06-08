package com.fengzhu.reading.interceptors;

import com.fengzhu.reading.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public CommonResponse<String> exceptionHandler(Throwable e){
        log.error("catch uncaught exception", e);
        return CommonResponse.newFail("系统内部错误：" + e.getMessage(), null);
    }

}
