package com.huang;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.huang.Dao.UserMapper;
import com.huang.Utils.JWTUtils;
import com.huang.Utils.RedisUtil;

import com.huang.pojo.Blog;
import com.huang.pojo.User;
import com.huang.service.BlogService;
import com.huang.service.Imp.UserServiceImp;
import com.huang.service.UserService;
import org.springframework.util.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    UserService userService;
    @Autowired
    private BlogService blogService;
    @Test
    void contextLoads(){
//        System.out.println(dataSource.getClass());
//        Connection connection = dataSource.getConnection();
//        System.out.println(connection);
//        List<OrderInfo> list = orderMapper.searchAll();
//        for (OrderInfo info : list) {
//            System.out.println(info);

//        redisTemplate.opsForValue().set("xixix","hahah");
//        System.out.println(redisTemplate.opsForValue().get("xixix"));
//        Set<String> keys = redisUtil.keys("*");
//        System.out.println(keys);
//        System.out.println((String) redisUtil.get("redis键"));
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();
//        System.out.println("数据库查询结果"+userServiceImp.searchByname("admin"));
//        HashMap<String, String> map = new HashMap<>();
//        map.put("username","admin");
//        map.put("password","123456");
//        String token = jwtUtils.getToken(map);
//        System.out.println(token);
//        String s = DigestUtils.md5DigestAsHex("20010906".getBytes()).toUpperCase();
//        System.out.println(s);
        List<Blog> blogs = blogService.searchAll(5);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }


    }

    }

