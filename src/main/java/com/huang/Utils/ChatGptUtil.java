package com.huang.Utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class ChatGptUtil {

    public String chat(String text){
        HttpRequest chat = HttpUtil.createPost("https://openai.nooc.ink/v1/chat/completions");
//        HttpRequest chat = HttpUtil.createPost("https://openai-openai-cqvpxofrvy.cn-hongkong.fcapp.run/v1/chat/completions");
        chat.header("Content-Type","application/json");
        chat.header("Authorization","Bearer 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");
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("model","gpt-3.5-turbo");
        ArrayList<Map> list = new ArrayList<>();
        HashMap<String, Object> map1 = new LinkedHashMap<>();
        map1.put("role","user");
        map1.put("content",text);
        list.add(map1);
        map.put("messages",list);
        map.put("temperature",0.7);
        chat.body(JSON.toJSONString(map));
        HttpResponse response = chat.execute();
        log.info(response.body().toString());
        Map parseObject = JSON.parseObject(response.body(), Map.class);
        List choices = (List) parseObject.get("choices");
        Map mess = (Map) choices.get(0);
        Map message = (Map) mess.get("message");
        return message.get("content").toString();

    }
}
