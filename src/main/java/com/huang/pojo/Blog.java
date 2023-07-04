package com.huang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private int id;
    private int user_id;
    private String title;
    private String description;
    private String content;
    private LocalDateTime created;
    private int status;
    public String writter;
}
