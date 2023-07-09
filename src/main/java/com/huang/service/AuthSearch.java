package com.huang.service;

import com.huang.Utils.JWTUtils;
import com.huang.Utils.RedisJsonUtil;
import com.huang.Utils.Result;
import com.huang.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class AuthSearch {
    @Autowired
    JWTUtils jwtUtils;
    @Autowired
    RedisJsonUtil redisJsonUtil;

    public boolean blogauth(HttpServletRequest req,int user_id){
        String token =req.getHeader("Authorization");
        String username = String.valueOf(jwtUtils.getTokenInfo(token).getClaim("username")).trim().replace("\"", "");
        int userid = Integer.parseInt(redisJsonUtil.get(username, "userid"));
        if( user_id== userid){
            return true;
        }else {
            return false;
        }
    }
}
