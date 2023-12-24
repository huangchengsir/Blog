package com.huang.common;


import com.huang.Enum.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.SocketTimeoutException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*
    * 处理空指针异常
    * @param e
    * @param req
    */
    @ExceptionHandler(value = NullPointerException.class)
    public BaseResponse NullexceptionHandler(HttpServletRequest req, NullPointerException e){
        log.info("空指针异常："+e.toString());
        e.printStackTrace();
        return BaseResponse.fail("空指针异常,原因："+"\n错误请求："+req);
    }
    /*
     * 处理IO异常
     * @param e
     * @param req
     */
    @ExceptionHandler(value = IOException.class)
    public BaseResponse IoexceptionHandler(HttpServletRequest req, IOException e){
        log.info("IO异常："+e);
        e.printStackTrace();
        return BaseResponse.fail("IO异常,原因："+e);
    }
    /*
     * 处理socket连接超时异常
     * @param e
     * @param req
     */
    @ExceptionHandler(value = SocketTimeoutException.class)
    public BaseResponse SocketExceptionHandler(HttpServletRequest req,SocketTimeoutException e){
        log.info("网络超时"+e);
        e.printStackTrace();
        return BaseResponse.fail("socket连接超时:"+e);
    }

    /**
     * @ParamType: [javax.servlet.http.HttpServletRequest, com.huang.common.HttpsException]
     * @param req
     * @param e
     * @return: com.huang.common.BaseResponse
     * @Author: hjw
     * @Date: 11:59 2023/9/23
     * @Description: 全局抛出自定义异常处理
     */
    @ExceptionHandler(value = HttpsException.class)
    public BaseResponse HttpExceptionHandler(HttpServletRequest req, HttpsException e){
        if(e.getCode()!=0){
            return BaseResponse.fail(e.getCode(),e.getMsg(),e.getData());
        }
        return BaseResponse.fail(ResultCodeEnum.UNKONWN_REASON.getCode(),e.getMsg(),e.getData());
    }


}
