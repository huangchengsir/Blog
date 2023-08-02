package com.huang.controller;


import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huang.Utils.*;
import com.huang.pojo.Blog;
import com.huang.pojo.PageData;
import com.huang.pojo.User;
import com.huang.service.AuthSearch;
import com.huang.service.BlogService;
import com.huang.service.SysService;
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
    @Autowired
    RedisJsonUtil redisJsonUtil;
    @Autowired
    SysService sysService;
    @Autowired
    AuthSearch authSearch;


    @ApiOperation("博客列表拉取接口")
    @GetMapping("/blogs")
    public Result list(
                        @RequestParam("currentPage") Integer currentPage,
                        @RequestParam("pageNum") Integer PageNum,
                        HttpServletRequest request){
        String token =request.getHeader("Authorization");
        String username = String.valueOf(jwtUtils.getTokenInfo(token).getClaim("username")).trim().replace("\"", "");
        log.info("用户名："+username);
        User user = userService.searchByname(username);
        int otherblog;
        if(sysService.search(user.getId())==null){
            otherblog=0;
        }else {
            otherblog=sysService.search(user.getId()).getOtherblog();
        }
        List<Blog> blogs;
        Page<Object> page;
        if(otherblog ==1){
            page = PageHelper.startPage(currentPage, PageNum);
            blogs = blogService.searchAll(currentPage,PageNum,user.getId());
        }else {
            page = PageHelper.startPage(currentPage, PageNum);
            blogs = blogService.searchOwn(currentPage,PageNum,user.getId());
        }
        try {
            for (Blog blog : blogs) {
                String blogwritter = userService.searchByid(blog.getUser_id()).getUsername();
                blog.setWritter(blogwritter);
            }

            pageData.setBlogs(blogs);
            pageData.setCurrent(page.getPages());
            pageData.setSize(page.getPageSize());
            pageData.setTotal((int)page.getTotal());
            return Result.succ(pageData);
        }
        catch (Exception e){
            return Result.fail(e.toString());
        }
    }

    @ApiOperation("博客详细信息接口")
    @GetMapping("/blog/{id}")
    public Result list(@PathVariable(name = "id") int id
                       ){
        Blog blog = blogService.searchByid(id);
            Assert.notNull(blog,"该博客已被删除");
            return Result.succ(blog);
    }
    @ApiOperation("博客编辑信息接口")
    @GetMapping("/blog/info/{id}")
    public Result listinfo(@PathVariable(name = "id") int id,
                       HttpServletRequest request){
        Blog blog = blogService.searchByid(id);
        if(authSearch.blogauth(request,blog.getUser_id())){
            Assert.notNull(blog,"该博客已被删除");
            return Result.succ(blog);
        }else {
            Assert.notNull(blog,"该博客已被删除");
            return Result.fail("没有权限编辑当前博客");
        }
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
            log.info(LocalDateTime.now().toString());
            tmp.setStatus(0);
            BeanUtil.copyProperties(blog, tmp, "id", "user_id", "created", "status");
            log.info(tmp.toString());
            blogService.Insert(tmp);
        }
        return Result.succ("编辑成功");
    }
    @ApiOperation("博客删除接口提供")
    @PostMapping("/blogs/delete")
    public Result delete(@RequestParam("id")int blogid,
                         HttpServletRequest request){
        log.info("进入了删除博客方法");
        String token =request.getHeader("Authorization");
        String localusername = String.valueOf(jwtUtils.getTokenInfo(token).getClaim("username")).trim().replace("\"", "");
        int userid = Integer.parseInt(redisJsonUtil.get(localusername, "userid"));
        Blog blog = blogService.searchByid(blogid);
        if(blog.getUser_id() == userid){
            blogService.DeleteByid(blogid);
            return Result.succ("删除成功");
        }else {
            return Result.fail("您没有权限删除当前博客");
        }
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
        //公开权限博客查询
        log.info("用户名："+username);
        User user = userService.searchByname(username);
        String filter = json.get("filter").toString();
        int otherblog;
        if(sysService.search(user.getId())==null){
            otherblog=0;
        }else {
            otherblog=sysService.search(user.getId()).getOtherblog();
        }
        List<Blog> blogs;
        if(otherblog ==1){
            blogs = blogService.searchByOwnfilter(filter,user.getId());
        }else {
            blogs = blogService.searchByfilter(filter,user.getId());
        }
        try {
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
