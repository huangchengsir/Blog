package com.huang.pojo.operate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class after_sale_address_detail {
    private String province_name;
    private String city_name;
    private String town_name;
    private String street_name;
    private String detail;
    private String user_name;
    private String mobile;
    private String province_id;
    private String city_id;
    private String town_id;
    private String street_id;

}
