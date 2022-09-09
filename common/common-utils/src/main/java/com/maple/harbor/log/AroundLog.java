package com.maple.harbor.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AroundLog {

    /**
     * 是否打印参数
     */
    boolean printParams() default true;

    /**
     * 是否打印结果
     */
    boolean printResult() default true;

    /**
     * 是否打印异常信息
     */
    boolean printException() default true;

}
