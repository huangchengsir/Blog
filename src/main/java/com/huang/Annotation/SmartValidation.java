package com.huang.Annotation;

import com.huang.Enum.ValidationType;

import java.lang.annotation.*;

/**
 * @program: Blog
 * @author: hjw
 * @create: 2023-12-20 22:36
 * @ClassName:SmartValidation
 * @Description: 万能参数校验，只能在参数中注解
 **/

@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SmartValidation {
    String message() default "参数校验失败";
    ValidationType type() default ValidationType.DEFAULT;
    String value() default "";
}
