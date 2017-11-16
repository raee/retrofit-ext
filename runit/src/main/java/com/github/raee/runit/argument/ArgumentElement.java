package com.github.raee.runit.argument;

/**
 * 测试元素定义为一条测试方法。
 * Created by ChenRui on 2017/1/10 0010 11:30.
 */
public @interface ArgumentElement {

    String name() default "";

    /**
     * 按照目标的测试方法的顺序一个个添加
     *
     * @return
     */
    String[] value();

    /**
     * 错误提示信息
     *
     * @return
     */
    String errorMessage() default "";

    /**
     * 调用返回成功信息，会以错误消息的方式提示。
     * 运用场景，比如：登录输入一下错误信息，但是接口却返回成功了，就再这里设置一些提示信息。
     *
     * @return
     */
    String successMessage() default "";
}
