package com.huang.pojo.operate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class logistics {
    private String company_code;
    private String logistics_code;
    private String receiver_address_id;
    private String sender_address_id;
    private com.huang.pojo.operate.after_sale_address_detail after_sale_address_detail;
}
