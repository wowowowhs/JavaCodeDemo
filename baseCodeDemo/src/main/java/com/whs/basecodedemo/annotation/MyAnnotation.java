package com.whs.basecodedemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *     SOURCE,    // 源文件保留
 *     CLASS,     // 编译期保留，默认值
 *     RUNTIME    // 运行期保留，可通过反射去获取注解信息
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD,})
public @interface MyAnnotation {
}
