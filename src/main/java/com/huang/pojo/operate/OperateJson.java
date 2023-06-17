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
public class OperateJson {
    private String appId;
    private String accessToken;
    private int type;
    private List<com.huang.pojo.operate.items> items;
    private String store_id;
}
