package com.huang.controller;


import com.alibaba.fastjson.JSON;
import com.huang.Annotation.ParamCheck;
import com.huang.Annotation.SmartValidation;
import com.huang.Dto.LoginDto;
import com.huang.Utils.JWTUtils;
import com.huang.Utils.RedisUtil;
import com.huang.Utils.Result;
import com.huang.pojo.Blog;
import com.huang.pojo.User;
import com.huang.service.BlogService;
import com.huang.service.Imp.UserServiceImp;
import com.huang.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@Slf4j
public class AutoController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private UserService userService;

    @ApiOperation("博客登录接口")
    @PostMapping("/login")
    @ParamCheck
    public Result Login(@RequestBody LoginDto map,
                        HttpServletResponse response
                        ){
        String username =  map.getUsername();
        String password =  map.getPassword();
        log.info("登录传入密码为:"+password);
        //获取当前用户对象subject，shiro自带方法
        Subject subject = SecurityUtils.getSubject();
        String Md5pwd = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();
        UsernamePasswordToken token = new UsernamePasswordToken(username,Md5pwd);
        HashMap<String, String> hashmap = new HashMap<>();
        //获取用户id
        hashmap.put("username",username);
        hashmap.put("password",password);
//        log.info(user.toString());
        log.info("进入了登录方法");
        try {
            User user = userServiceImp.searchByname(username);
            subject.login(token);
            hashmap.put("userId",String.valueOf(user.getId()));
            String jwt = jwtUtils.getToken(hashmap);
            HashMap<Object, Object> redismap = new HashMap<>();
            //存入redis的数据
            redismap.put("username",username);
            redismap.put("userid",String.valueOf(user.getId()));
            redismap.put("Authorization", jwt);
            redismap.put("logintime", LocalDateTime.now());
            //存入Redis缓存
            redisUtil.set(username,JSON.toJSON(redismap),10800);
            Result result = Result.succ(200, "登录成功", user);
            userService.Updatetime(username);
            response.setHeader("Authorization", jwt);
            response.setHeader("Access-control-Expose-Headers", "Authorization, Set-Cookie");
            return result;
        } catch (UnknownAccountException e) {
            Result result = Result.fail(500, "用户名错误", "请重新输入");
            return result;
        } catch (IncorrectCredentialsException e){
            Result result = Result.fail(500, "密码错误", "请重新输入");
            return result;
        }
    }

    @ApiOperation("退出登录接口")
    @GetMapping("/logout")
    public Result logout(){
        log.info("进入了退出登录方法");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.succ("退出登录后，当前用户是否认证："+subject.isAuthenticated());
    }

}
