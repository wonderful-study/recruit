package com.hzu.recruit.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
//异常处理器（可以自定义日志输出）
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    //此注解表示哪些异常触发这个处理器
    @ExceptionHandler(value = {Exception.class,RuntimeException.class})
    public String error500(HttpServletRequest request,Exception e) {
        //输出日志异常信息
        logger.error(e.getMessage(),e);
        //输出request的请求路径
        logger.error(request.getRequestURL() + "  encounter 500");
        return "error/500";
    }
}
