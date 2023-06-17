package com.huang.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class OrderClass {
    @Value("SKD202306140001")
    private String ordernum;
    @Value("抖音入库单")
    private String ordername;
    @Value("这是一个用于测试抖音单据的单据")
    private String desc;
    @Value("601")
    private int status;
}
