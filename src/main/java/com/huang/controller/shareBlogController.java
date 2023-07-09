package com.huang.controller;

import com.huang.Utils.NormalUtil;
import com.huang.Utils.Result;
import com.huang.pojo.Blog;
import com.huang.service.AuthSearch;
import com.huang.service.BlogService;
import com.huang.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/share")
public class shareBlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
    @Autowired
    AuthSearch authSearch;

    @ApiOperation("公开博客接口")
    @GetMapping("/public")
    public Result sharpublic(
            @RequestParam("blogid") int blogid,
            @RequestParam("status") boolean status,
            HttpServletRequest request
    ){
        int i = NormalUtil.boolToInt(status);
        Blog blog = blogService.searchByid(blogid);
        try{
            if(authSearch.blogauth(request,blog.getUser_id())){
                blogService.Updatestatus(blogid,i);
                if(i==1){
                    return Result.succ("博客状态更改成功，当前状态为:公开");
                }else {
                    return Result.succ("博客状态更改成功，当前状态为:私人");

                }
            }else {
                return Result.fail("您没有权限操作当前博客");
            }
        }catch (Exception e){
            return Result.fail(e.toString());
        }



    }
}
