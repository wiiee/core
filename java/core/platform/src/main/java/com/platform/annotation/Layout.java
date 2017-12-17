package com.platform.annotation;

import java.lang.annotation.*;

/**
 * Created by wang.na on 2017/3/20.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Layout {
    String NONE = "none"; // no layout will be used

    String value();
}