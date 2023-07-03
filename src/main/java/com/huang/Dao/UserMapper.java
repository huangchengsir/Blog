package com.huang.Dao;


import com.huang.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    User searchAll();
    User searchByid(@Param("id") int id);
    void Update(User user);
    void DeleteByid(@Param("id") int id);
    void Insert(User user);
    User searchByname(@Param("name") String name);
    void Updatetime(@Param("username")String username);
}
