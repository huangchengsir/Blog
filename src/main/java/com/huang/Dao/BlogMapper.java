package com.huang.Dao;


import com.huang.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BlogMapper {
    List<Blog> searchAll(Integer page, int id);
    Blog searchByid(@Param("id") int id);
    void Update(Blog blog);
    void DeleteByid(@Param("id") int id);
    void Insert(Blog blog);
    List<Blog> searchByfilter(@Param("filter") String filter,int id);
}
