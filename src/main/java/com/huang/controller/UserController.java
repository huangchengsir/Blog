package com.huang.controller;


import com.huang.Utils.JWTUtils;
import com.huang.Utils.RedisUtil;
import com.huang.Utils.Result;
import com.huang.pojo.User;
import com.huang.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private User user;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @ApiOperation("用户注册接口")
    @PostMapping("/register")
    public Result regis(@RequestParam("username") String username,
                        @RequestParam("avatar") String avatar,
                        @RequestParam("email") String email,
                        @RequestParam("password") String password
                        ){
        user.setUsername(username);
        user.setAvatar(avatar);
        user.setEmail(email);
        user.setPassword(password);
        User localuser = userService.searchByname(username);
        if(localuser==null){
            userService.Insert(user);
            return Result.succ("注册成功");
        }
        else {
            return Result.fail("已有同名用户，请修改后重新注册");
        }
    }
    @ApiOperation("用户编辑接口")
    @PostMapping("/edit")
    public Result edit(@RequestParam("username") String username,
                       @RequestParam("avatar") String avatar,
                       @RequestParam("email") String email,
                       @RequestParam("password") String password,
                       HttpServletRequest request,
                       HttpServletResponse response){
        String token =request.getHeader("Authorization");
        String localusername = String.valueOf(jwtUtils.getTokenInfo(token).getClaim("username")).trim().replace("\"", "");
        User localuser = userService.searchByname(localusername);
        try{
            int id = localuser.getId();
            if(id != 0){
                user.setId(id);
                user.setUsername(username);
                user.setAvatar(avatar);
                user.setEmail(email);
                user.setPassword(password);
                userService.Update(user);
//                HashMap<String, String> hashmap = new HashMap<>();
//                //获取用户id
//                hashmap.put("username",username);
//                hashmap.put("password",password);
//                hashmap.put("userId",String.valueOf(id));
//                String jwt = jwtUtils.getToken(hashmap);
//                redisUtil.set("Authorization",jwt,10800);
//                response.setHeader("Authorization", jwt);
//                response.setHeader("Access-control-Expose-Headers", "Authorization, Set-Cookie");
                return Result.succ("修改成功");
            }
            else{
                return Result.fail("当前用户不存在，请重新登陆");
            }
        }catch (Exception e){
            return Result.fail("认证信息错误，请尝试重新登陆后操作");
        }
    }
    @ApiOperation("用户信息获取接口")
    @GetMapping("/info")
    public Result info(HttpServletRequest request){
        String token =request.getHeader("Authorization");
        String userId = String.valueOf(jwtUtils.getTokenInfo(token).getClaim("userId")).trim().replace("\"", "");
        int num = Integer.parseInt(userId);
        User userinfo = userService.searchByid(num);
        return Result.succ(userinfo);
    }
}
