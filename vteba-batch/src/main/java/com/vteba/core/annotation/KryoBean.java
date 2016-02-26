package com.vteba.core.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 表示是Kryo所使用的Bean。
 * 
 * @author yinlei
 * @date 2016年2月26日 下午6:07:39
 */
@Retention(RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface KryoBean {

}
