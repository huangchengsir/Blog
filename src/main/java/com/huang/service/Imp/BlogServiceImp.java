package com.huang.service.Imp;

import com.huang.Dao.BlogMapper;
import com.huang.pojo.Blog;
import com.huang.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BlogServiceImp implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Override
    public List<Blog> searchAll(Integer page ,int id) {
        return blogMapper.searchAll(page, id);
    }

    @Override
    public Blog searchByid(int id) {
        return blogMapper.searchByid(id);
    }

    @Override
    public void Update(Blog blog) {
        blogMapper.Update(blog);
    }

    @Override
    public void DeleteByid(int id) {
        blogMapper.DeleteByid(id);
    }

    @Override
    public void Insert(Blog blog) {
        blogMapper.Insert(blog);
    }

    @Override
    public List<Blog> searchByfilter(String filter, int id) {
        return blogMapper.searchByfilter(filter,id);
    }
}
