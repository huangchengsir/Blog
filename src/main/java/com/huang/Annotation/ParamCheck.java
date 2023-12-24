package com.huang.Annotation;

import java.lang.annotation.*;

/**
 * @program: Blog
 * @author: hjw
 * @create: 2023-12-24 14:26
 * @ClassName:ParamCheck
 * @Description:
 **/

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamCheck {
}
