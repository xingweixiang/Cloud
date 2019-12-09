package com.cloudnative.logService.annotation;

import java.lang.annotation.*;

/**
 * Created by xingwx on 2019/12/7
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    /**
     * 模块
     * @return
     */
    String module();

    /**
     * 记录执行参数
     * @return
     */
    boolean recordRequestParam() default true;
}
