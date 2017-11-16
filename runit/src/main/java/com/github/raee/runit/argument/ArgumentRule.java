package com.github.raee.runit.argument;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 参数化测试规则
 * Created by ChenRui on 2017/1/10 0010 11:20.
 */
@Target(ElementType.METHOD)
public @interface ArgumentRule {

    /**
     * 通过配置文件的方法
     */
    @Target(ElementType.METHOD)
    @interface configMethod {

        /**
         * 配置文件路径
         *
         * @return
         */
        String path() default "";

        /**
         * 引用测试文件的测试名称，请参考标准：<br>
         * {
         * "pathName":[...argumentElement...]
         * }
         *
         * @return
         */
        String value() default "";
    }

    /**
     * 是否为异步调用，默认为是
     *
     * @return
     */
    boolean isAsyncTask() default true;

    /**
     * 一组测试
     *
     * @return
     */
    ArgumentElement[] value() default {};
}
