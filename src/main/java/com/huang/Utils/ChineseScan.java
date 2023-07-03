package com.huang.Utils;

import org.springframework.stereotype.Component;

@Component
public class ChineseScan {
    public static boolean containsChinese(String str){
        if(str == null || str.trim().isEmpty()) {
            return false; // 空字符串或 null 不包含中文
        }
        String regex = "[\\u4e00-\\u9fa5]"; // 中文 Unicode 范围
        return str.matches(".*" + regex + ".*");
    }
    }
