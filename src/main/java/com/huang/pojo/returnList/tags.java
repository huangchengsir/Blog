package com.huang.pojo.returnList;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class tags {
    private String tag_detail;
    private String tag_detail_en;
    private String tag_link_url;
}
