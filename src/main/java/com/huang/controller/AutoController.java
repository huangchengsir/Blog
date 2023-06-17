package com.huang.controller;


import com.huang.Utils.Result;
import com.huang.pojo.OrderClass;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutoController {
    @Autowired
    private Result result;
    @Autowired
    private OrderClass orderClass;

    @ApiOperation("抖店mock接口")
    @GetMapping("/login")
    public Result Login(@RequestParam("name") String username,@RequestParam("password") String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        System.out.println("用户名："+username+"\n密码："+password);
        try {
            subject.login(token);
            result.setCode(200);
            result.setMsg("登录成功");
            result.setSub_msg(orderClass);
            return result;
        } catch (UnknownAccountException e) {
            result.setMsg("用户名错误");
            result.setCode(500);
            return result;
        } catch (IncorrectCredentialsException e){
            result.setMsg("密码错误");
            result.setCode(500);
            return result;
        }

    }
}
