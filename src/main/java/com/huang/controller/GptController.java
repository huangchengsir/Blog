package com.huang.controller;


import com.huang.Utils.ChatGptUtil;
import com.huang.Utils.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/chat")
@Slf4j
public class GptController {
    @Autowired
    private ChatGptUtil chatGptUtil;

    @ApiOperation("chatgpt对接接口")
    @PostMapping("talk")
    public Result chat(@RequestBody Map<String,Object> json){
        String content = json.get("content").toString();
        try{
            String chat = chatGptUtil.chat(content);
            return Result.succ(chat);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }
}
