package com.huang.pojo.returnList;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class relatedOrderInfo {
    private String sku_order_id;
    private int order_status;
    private int pay_amount;
    private String post_amount;
    private int item_num;
    private String create_time;
    private String tax_amount;
    private String is_oversea_order;
    private String product_name;
    private String product_id;
    private String product_image;
    private tags tags;
    private skuSpec sku_spec;
    private String shop_sku_code;
    private String logistics_code;
    private String aftersale_pay_amount;
    private String aftersale_post_amount;
    private int aftersale_tax_amount;
    private int aftersale_item_num;
    private String promotion_pay_amount;
    private int price;
    private String logistics_company_name;
    private String given_sku_order_ids;
}
