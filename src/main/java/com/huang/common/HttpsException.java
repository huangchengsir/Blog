package com.huang.common;

import lombok.Data;

/**
 * @program: clothing-applet
 * @author: hjw
 * @create: 2023-09-23 11:38
 * @ClassName:CommonException
 * @Description:
 **/
@Data
public class HttpsException extends RuntimeException{

    private int code = 0;
    private String msg;
    private Object data;

    //对该异常类的构造方法进行补充，不写的化会默认只有一个无参构造
    public HttpsException(String msg) {
        this.msg = msg;
    }

    public HttpsException(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public HttpsException(String msg, Throwable e) {
        this.msg = msg;
        this.data = e.getLocalizedMessage();
    }

    public HttpsException(String msg, int code, Throwable e) {
        this.msg = msg;
        this.code = code;
        this.data = e.getLocalizedMessage();
    }


}
