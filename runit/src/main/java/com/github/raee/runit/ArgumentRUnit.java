package com.github.raee.runit;

import com.github.raee.runit.argument.ApiFromJsonInvokeMethod;
import com.github.raee.runit.argument.ApiInvokeMethod;
import com.github.raee.runit.argument.ArgumentRule;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * API 参数化测试
 * Created by ChenRui on 2017/1/10 0010 11:48.
 */
public final class ArgumentRUnit extends BlockJUnit4ClassRunner {

    private RunNotifier mNotifier;

    public ArgumentRUnit(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        super.runChild(method, notifier);
        mNotifier = notifier;
    }

    @Override
    protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors) {
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        ArgumentRule.configMethod annotation = method.getAnnotation(ArgumentRule.configMethod.class);
        if (annotation != null) {
            return new ApiFromJsonInvokeMethod(getTestClass(), method, test);
        }
        return new ApiInvokeMethod(getTestClass(), method, test);
    }
}
