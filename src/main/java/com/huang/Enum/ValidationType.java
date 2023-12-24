package com.huang.Enum;

/**
 * @program: Blog
 * @author: hjw
 * @create: 2023-12-20 22:41
 * @ClassName:ValidationType
 * @Description: 参数校验类型
 **/
public enum ValidationType {
    DEFAULT,
    PASSWORD,
    USERNAME,
    EMAIL,
    //数字即数字长度校验需要注解为[min,max]
    NUMBER,
}
