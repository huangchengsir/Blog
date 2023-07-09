package com.huang.controller;


import com.huang.Utils.JWTUtils;
import com.huang.Utils.RedisUtil;
import com.huang.Utils.Result;
import com.huang.pojo.SysSetting;
import com.huang.pojo.User;
import com.huang.service.SysService;
import com.huang.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @Autowired
    private SysService sysService;
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
    @ApiOperation("博客作者获取接口")
    @GetMapping("/blog")
    public Result blog(@RequestParam("id") int id){
        User user = userService.searchByid(id);
        return Result.succ(user);
    }
    @ApiOperation("系统设置获取接口")
    @GetMapping("/setting")
    public Result setting(@RequestParam("id") int id){
        try{
            SysSetting search = sysService.search(id);
            if(search==null){
                return Result.succ(new SysSetting(1,id,0));
            }else {
                return Result.succ(search);
            }
        }catch (Exception e){
            return Result.fail(e.toString());
        }
    }
    @ApiOperation("系统设置修改接口")
    @GetMapping("/setting/change")
    public Result change(@RequestParam("id") int id,
                         @RequestParam("otherblog") int otherblog){
        try {
            SysSetting search = sysService.search(id);
            if(search==null){
                sysService.insert(id, otherblog);
            }else {
                sysService.UpdateSetting(id, otherblog);
            }
            return Result.succ("配置保存成功");
        }catch (Exception e){
            return Result.fail(e.toString());
    }
}
}
