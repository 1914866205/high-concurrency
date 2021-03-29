package com.soft.usercenter.annotation;

import java.lang.annotation.*;

/**
 * 自定义的Web日志注解
 *
 * @Documented 可被文档托管  属于lang包
 * @Retention(RetentionPolicy.RUNTIME) 指定作用域生命周期——运行时有效
 * @Target 目标是方法级
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ControllerWebLog {
    //调用的接口名称
    String name();

    //该日志是否需要持久化存储*
    boolean isSaved() default false;

}
