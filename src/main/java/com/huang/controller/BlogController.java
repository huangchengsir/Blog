package com.huang.controller;


import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.huang.Utils.ChineseScan;
import com.huang.Utils.JWTUtils;
import com.huang.Utils.Result;
import com.huang.pojo.Blog;
import com.huang.pojo.PageData;
import com.huang.pojo.User;
import com.huang.service.BlogService;
import com.huang.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public Result list(@RequestParam(defaultValue = "10") Integer currentPage,
                        HttpServletRequest request){
        String token =request.getHeader("Authorization");
        String username = String.valueOf(jwtUtils.getTokenInfo(token).getClaim("username")).trim().replace("\"", "");
        log.info("用户名："+username);
        User user = userService.searchByname(username);
        PageHelper.startPage(1,currentPage);
        List<Blog> blogs = blogService.searchAll(currentPage,user.getId());
        try {
            for (Blog blog : blogs) {
                String blogwritter = userService.searchByid(blog.getUser_id()).getUsername();
                blog.setWritter(blogwritter);
            }
            pageData.setBlogs(blogs);
            pageData.setCurrent(currentPage);
            pageData.setSize(blogs.size());
            pageData.setTotal(blogs.size());
            return Result.succ(pageData);
        }
        catch (Exception e){
            return Result.fail(e.toString());
        }
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

    @ApiOperation("图片上传接口")
    @PostMapping("/upload-image")
    public Result picture(@RequestParam("image") MultipartFile image,
                          HttpServletRequest request){
        String token =request.getHeader("Authorization");
        String localusername = String.valueOf(jwtUtils.getTokenInfo(token).getClaim("username")).trim().replace("\"", "");
        if (image.isEmpty()) {
            return Result.fail("上传的图片为空");
        }
        // 获取保存图片的目录路径
        String imagesDir = "src/main/resources/static/images";
        String savePath = System.getProperty("user.dir") + File.separator + imagesDir;

        // 创建目录
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            // 生成保存图片的文件路径
            String fileName = image.getOriginalFilename();
            int index = fileName.lastIndexOf(".");
            if (index >= 0 && index + 1 < fileName.length()) {
                String extension = fileName.substring(index);
                if (ChineseScan.containsChinese(fileName)){
                    fileName = localusername+ UUID.randomUUID().toString()+extension;
                }
            }
            String filePath = savePath + File.separator + fileName;
            // 保存图片
            image.transferTo(new File(filePath));
            // 返回图片访问路径
            String imageUrl = "/images/" + fileName;
            return Result.succ(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("图片上传失败");
        }
    }
    @ApiOperation("注册图片上传接口")
    @PostMapping("/regis_upload-image")
    public Result regispicture(@RequestParam("image") MultipartFile image){
        String localusername = "Newuser";
        if (image.isEmpty()) {
            return Result.fail("上传的图片为空");
        }
        // 获取保存图片的目录路径
        String imagesDir = "src/main/resources/static/images";
        String savePath = System.getProperty("user.dir") + File.separator + imagesDir;

        // 创建目录
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            // 生成保存图片的文件路径
            String fileName = image.getOriginalFilename();
            int index = fileName.lastIndexOf(".");
            if (index >= 0 && index + 1 < fileName.length()) {
                String extension = fileName.substring(index);
                if (ChineseScan.containsChinese(fileName)){
                    fileName = localusername+ UUID.randomUUID().toString()+extension;
                }
            }
            String filePath = savePath + File.separator + fileName;
            // 保存图片
            image.transferTo(new File(filePath));
            // 返回图片访问路径
            String imageUrl = "/images/" + fileName;
            return Result.succ(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("图片上传失败");
        }
    }

    @ApiOperation("博客搜索接口")
    @PostMapping("/blog/search")
    public Result search(@RequestBody Map<String,Object> json,
                         HttpServletRequest request){
        String token =request.getHeader("Authorization");
        String username = String.valueOf(jwtUtils.getTokenInfo(token).getClaim("username")).trim().replace("\"", "");
        try {
            User user = userService.searchByname(username);
            String filter = json.get("filter").toString();
            List<Blog> blogs = blogService.searchByfilter(filter,user.getId());
            for (Blog blog : blogs) {
                String blogwritter = userService.searchByid(blog.getUser_id()).getUsername();
                blog.setWritter(blogwritter);
            }
            pageData.setBlogs(blogs);
            pageData.setCurrent(blogs.size());
            pageData.setSize(blogs.size());
            pageData.setTotal(blogs.size());
            return Result.succ(pageData);
        }catch (Exception e){
            return Result.fail("token错误，请重新登录");
        }

    }
}
