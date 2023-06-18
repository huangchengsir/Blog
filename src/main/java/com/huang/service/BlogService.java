package com.huang.service;

import com.huang.pojo.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;


@Service
public interface BlogService {
    Blog searchAll();
    Blog searchByid(@Param("id") int id);
    void Update(Blog blog);
    void DeleteByid(@Param("id") int id);
    void Insert(Blog blog);
}
