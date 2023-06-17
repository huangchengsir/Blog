package com.huang.pojo.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ListJson {
    private String appId;
    private String accessToken;
    private String order_id;
    private int aftersale_type;
    private int aftersale_status;
    private int reason;
    private int logistics_status;
    private int pay_type;
    private int refund_type;
    private List<String> arbitrate_status;
    private List<Integer> order_flag;
    private String start_time;
    private String end_time;
    private int amount_start;
    private int amount_end;
    private int risk_flag;
    private List<String> order_by;
    private int page;
    private int size;
    private String aftersale_id;
    private List<Integer> standard_aftersale_status;
    private String need_special_type;
    private String update_start_time;
    private String update_end_time;
    private List<String> order_logistics_tracking_no;
    private List<Integer> order_logistics_state;
    private List<Integer> agree_refuse_sign;
    private int aftersale_sub_type;
    private String aftersale_status_to_final_start_time;
    private String aftersale_status_to_final_end_time;

}
