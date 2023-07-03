package com.huang.service.Imp;

import com.huang.Dao.UserMapper;
import com.huang.pojo.User;
import com.huang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User searchAll() {
        return userMapper.searchAll();
    }

    @Override
    public User searchByid(int id) {
        return userMapper.searchByid(id);
    }

    @Override
    public void Update(User user) {
        userMapper.Update(user);
    }

    @Override
    public void DeleteByid(int id) {
        userMapper.DeleteByid(id);
    }

    @Override
    public void Insert(User user) {
        userMapper.Insert(user);
    }

    @Override
    public User searchByname(String name) {
        return userMapper.searchByname(name);
    }

    @Override
    public void Updatetime(String username) {
        userMapper.Updatetime(username);
    }
}
