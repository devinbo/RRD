package com.rrd.annotation;

import java.lang.annotation.*;

/**
 * Created by xinghb on 2017/10/27.
 */
@Target(ElementType.METHOD) //注解描述使用在方法上
@Retention(RetentionPolicy.RUNTIME) //保留至运行时, 这样可通过反射获取到该注解
@Documented
public @interface RefushRedis {
    String id() default "";
    String clazz() default "Custom";
    String pack() default "com.rrd.model";
}
