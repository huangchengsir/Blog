package com.huang.controller;


import com.auth0.jwt.interfaces.Claim;
import com.github.pagehelper.PageHelper;
import com.huang.Utils.JWTUtils;
import com.huang.Utils.Result;
import com.huang.pojo.Blog;
import com.huang.pojo.User;
import com.huang.service.BlogService;
import com.huang.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@Slf4j
@Component
public class BlogController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private BlogService blogService;

    @ApiOperation("博客列表拉取接口")
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        PageHelper.startPage(1,currentPage);
        Blog blog = blogService.searchAll();
        return Result.succ(blog);
    }

    @ApiOperation("博客详细信息接口")
    @GetMapping("/detail/{id}")
    public Result list(@PathVariable(name = "id") int id){
        Blog blog = blogService.searchByid(id);
        Assert.notNull(blog,"该博客已被删除");
        return Result.succ(blog);
    }

    @ApiOperation("博客编辑接口")
    @PostMapping("/blog/edit")
    public Result list(@RequestBody Blog blog, HttpServletRequest request){
        String token =request.getHeader("Authorization");
        String username = String.valueOf(jwtUtils.getTokenInfo(token).getClaim("username")).trim().replace("\"", "");
        log.info("用户名："+username);
        User user = userService.searchByname(username);
        int id = user.getId();
        log.info("id="+id);
        Blog tmp = null;
        if(blog.getId() != 0){
            tmp = blogService.searchByid(blog.getId());
            Assert.isTrue(tmp.getUser_id() == id, "没有权限编辑");
        } else {
            tmp = new Blog();
            tmp.setTitle(blog.getTitle());
            tmp.setDescription(blog.getDescription());
            tmp.setContent(blog.getContent());
        }
        tmp.setCreated(LocalDateTime.now());
        tmp.setStatus(0);
        tmp.setUser_id(blog.getId());
        blogService.Update(tmp);

        return Result.succ("编辑成功");
    }
}
