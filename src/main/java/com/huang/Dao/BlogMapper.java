package com.huang.Dao;


import com.huang.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BlogMapper {
    Blog searchAll();
    Blog searchByid(@Param("id") int id);
    void Update(Blog blog);
    void DeleteByid(@Param("id") int id);
    void Insert(Blog blog);
}
