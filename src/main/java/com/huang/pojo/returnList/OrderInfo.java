package com.huang.pojo.returnList;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class OrderInfo {
    private String shop_order_id;
    private relatedOrderInfo related_order_info;
    private int order_flag;
}
