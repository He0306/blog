package com.hc.blog.annotation;

/**
 * @author: 何超
 * @date: 2022/10/21
 */


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    //操作类型
    String optType() default "";
}
