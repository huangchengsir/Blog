package com.huang.service;

import com.huang.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User searchAll();
    User searchByid(@Param("id") int id);
    void Update(User user);
    void DeleteByid(@Param("id") int id);
    void Insert(User user);
    User searchByname(@Param("name") String name);
    void Updatetime(@Param("username")String username);
}
