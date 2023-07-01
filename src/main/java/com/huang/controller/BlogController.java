package com.huang.controller;


import cn.hutool.core.bean.BeanUtil;
import com.auth0.jwt.interfaces.Claim;
import com.github.pagehelper.PageHelper;
import com.huang.Utils.JWTUtils;
import com.huang.Utils.Result;
import com.huang.pojo.Blog;
import com.huang.pojo.PageData;
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
import java.util.List;

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
    @Autowired
    private PageData pageData;

    @ApiOperation("博客列表拉取接口")
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "10") Integer currentPage){
        PageHelper.startPage(1,currentPage);
        List<Blog> blogs = blogService.searchAll(currentPage);
        log.info(blogs.toString());
        pageData.setBlogs(blogs);
        pageData.setCurrent(currentPage);
        pageData.setSize(blogs.size());
        pageData.setTotal(blogs.size());
        return Result.succ(pageData);
    }

    @ApiOperation("博客详细信息接口")
    @GetMapping("/blog/{id}")
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
            BeanUtil.copyProperties(blog, tmp, "id", "userId", "created", "status");
            tmp.setUser_id(id);
            blogService.Update(tmp);
        } else {
            tmp = new Blog();
            tmp.setUser_id(id);
            tmp.setCreated(LocalDateTime.now());
            tmp.setStatus(0);
            BeanUtil.copyProperties(blog, tmp, "id", "user_id", "created", "status");
            log.info(tmp.toString());
            blogService.Insert(tmp);
        }
        return Result.succ("编辑成功");
    }
    @ApiOperation("博客删除接口提供")
    @PostMapping("/blogs/delete")
    public Result delete(@RequestParam("id")int blogid){
        log.info("进入了删除博客方法");
        blogService.DeleteByid(blogid);
        return Result.succ("删除成功");
    }
}
