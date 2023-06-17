package com.huang.controller;


import com.huang.Utils.Result;
import com.huang.pojo.List.ListJson;
import com.huang.pojo.operate.OperateJson;
import com.huang.service.Imp.AfterSaleSearchImp;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/afterSale")
public class AfterSaleController {
    @Autowired
    private Result result;
    @Autowired
    private AfterSaleSearchImp searchImp;

    @ApiOperation("售后审核接口聚合版")
    @PostMapping("/operate")
    public Result operate(@RequestParam("appkey") String key,
                          @RequestParam("method") String method,
                          @RequestParam("access_token") String access_token,
                          @RequestParam("timestamp") String timestamp,
                          @RequestParam("v") String v,
                          @RequestParam("sign") String sign,
                          @RequestParam("type") int type,
                          @RequestBody OperateJson json){
        System.out.println("json数据为：\n"+json);
        System.out.println(json.getType());
        String status = searchImp.status(json.getType());
        result.setCode(200);
        result.setMsg(status);
        result.setSub_code(50002);
        result.setSub_msg("单据接口测试");
        System.out.println(result.toString());
        return result;
    }

    @ApiOperation("售后列表接口")
    @PostMapping("/List")
    public Result OrderList(@RequestParam("appkey") String key,
                            @RequestParam("method") String method,
                            @RequestParam("access_token") String access_token,
                            @RequestParam("timestamp") String timestamp,
                            @RequestParam("v") String v,
                            @RequestParam("sign") String sign,
                            @RequestParam("type") int type,
                            @RequestBody ListJson json){
        result.setCode(200);
        result.setMsg("单据列表获取成功");
        result.setSub_code(50002);
        result.setSub_msg(json);
        return result;
    }
    @ApiOperation("提供给商家获取售后单详情信息")
    @PostMapping("/Detail")
    public Result OrderDetail(@RequestParam("appkey") String key,
                            @RequestParam("method") String method,
                            @RequestParam("access_token") String access_token,
                            @RequestParam("timestamp") String timestamp,
                            @RequestParam("v") String v,
                            @RequestParam("sign") String sign,
                            @RequestParam("type") int type,
                            @RequestBody Map<String,Object> map){
        result.setCode(200);
        result.setMsg("单据详情获取成功");
        result.setSub_code(50002);
        result.setSub_msg("接口测试");
        return result;

    }



}
