package com.huang.Utils;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class RedisJsonUtil {
    @Autowired
    RedisUtil redisUtil;
    public String get(String name,String property){
        try{
            String value = redisUtil.get(name).toString();
            Map map = JSON.parseObject(value, Map.class);
            return map.get(property).toString();
        }catch (NullPointerException e){
            return null;
        }
    }
}
