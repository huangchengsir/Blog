package com.huang.pojo.operate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class items {
    private String aftersale_id;
    private String reason;
    private String remark;
    private List<Evidence> evidence;
    private com.huang.pojo.operate.logistics logistics;
    private int reject_reason_code;
    private String update_time;

}
