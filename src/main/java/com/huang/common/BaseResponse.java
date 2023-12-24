package com.huang.common;

import com.huang.Enum.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Data
@ApiModel(value = "全局统一返回结果")
public class BaseResponse<T> implements Serializable {

    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    public T data;

    public BaseResponse(Integer code) {
        this.code = code;
        this.message = null;
        this.data = null;
    }

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public BaseResponse(Integer code, T data) {
        this.message = "操作成功";
        this.code = code;
        this.data = data;
    }

    public BaseResponse(String message, T data) {
        this.code = HttpServletResponse.SC_OK;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static BaseResponse success() {
        return new BaseResponse<>(HttpServletResponse.SC_OK);
    }

    public static BaseResponse success(String message) {
        return new BaseResponse<>(HttpServletResponse.SC_OK, message);
    }

    public static BaseResponse success(Object data) {
        return new BaseResponse(HttpServletResponse.SC_OK, data);
    }

    public static BaseResponse success(String message, Object data) {
        return new BaseResponse(message, data);
    }

    public static BaseResponse fail(ResultCodeEnum resultCodeEnum) {
        return new BaseResponse<>(resultCodeEnum.getCode(), resultCodeEnum.getMessage());
    }
    public static BaseResponse fail(ResultCodeEnum resultCodeEnum,Object data) {
        return new BaseResponse<>(resultCodeEnum.getCode(), resultCodeEnum.getMessage(),data);
    }
    public static BaseResponse fail(Integer code, String message, Object data) {
        return new BaseResponse<>(code,message,data);
    }
    public static BaseResponse fail(Integer code, String message) {
        return new BaseResponse<>(code,message);
    }

    public static BaseResponse fail(String message){return new BaseResponse<>(400,message);};

}
