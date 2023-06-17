package com.huang.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Result implements Serializable {

    private int code;
    private String msg;
    private int sub_code;
    private Object sub_msg;

    public static Result success(Object data) {

        return success(200, "操作成功",200, data);
    }

    public static Result success(int code, String msg, int sub_code,Object sub_msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setSub_code(sub_code);
        result.setSub_msg(sub_msg);
        return result;
    }

    public static Result fail(String msg) {
        return fail(400, msg, 0,null);
    }

    public static Result fail(int code, String msg, int sub_code,Object sub_msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setSub_code(sub_code);
        result.setSub_msg(sub_msg);
        return result;
    }

}

