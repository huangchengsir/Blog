package com.huang.Utils;

import org.springframework.stereotype.Component;

@Component
public class NormalUtil {
    public static int boolToInt(Boolean b) {
        return b ? 1 : 0;
    }
}
