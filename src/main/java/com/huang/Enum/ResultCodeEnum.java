package com.huang.Enum;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200,"操作成功"),
    AUTHORITY_ERROR(403,"权限错误"),
    BAD_SQL_GRAMMAR(10001,"sql语法错误"),
    JSON_PARSE_ERROR(10002, "json解析异常"),
    PARAM_ERROR(10003, "参数不正确"),
    FILE_UPLOAD_ERROR(10004, "文件上传错误"),
    UNKONWN_REASON(10005,"未知错误"),
    NULLPOINTER_ERROR(10006,"空指针异常"),
    IO_ERROR(10007,"服务端IO异常"),
    TIMEOUT_EROOR(10008,"网络连接超时"),

    LOGIN_ERROR(10009,"微信官方登陆失败");

    private Integer code;
    private String message;
    private Object data;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
