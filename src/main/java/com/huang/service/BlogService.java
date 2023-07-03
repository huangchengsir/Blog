package com.huang.service;

import com.huang.pojo.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface BlogService {
    List<Blog> searchAll(Integer page,int id);
    Blog searchByid(@Param("id") int id);
    void Update(Blog blog);
    void DeleteByid(@Param("id") int id);
    void Insert(Blog blog);
    List<Blog> searchByfilter(@Param("filter") String filter,int id);
}
