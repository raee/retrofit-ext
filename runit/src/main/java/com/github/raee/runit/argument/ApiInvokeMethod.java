package com.github.raee.runit.argument;

import android.support.test.InstrumentationRegistry;

import com.github.raee.runit.RUnitArgumentTestListener;
import com.github.raee.runit.RUnitTestCaseQueue;
import com.github.raee.runit.RUnitTestCaseRunnable;
import com.github.raee.runit.RUnitTestError;
import com.github.raee.runit.RUnitTestRuntimeException;

import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * API 方法调用
 * Created by ChenRui on 2017/1/11 0011 11:04.
 */
public class ApiInvokeMethod extends InvokeMethod {

    protected final FrameworkMethod sMethod;
    protected final Object sTarget;
    protected final TestClass sTestClass;
    protected List<Throwable> errors = new ArrayList<>(); // 错误集合

    public ApiInvokeMethod(TestClass testClass, FrameworkMethod testMethod, Object target) {
        super(testMethod, target);
        sTestClass = testClass;
        sMethod = testMethod;
        sTarget = target;
    }

    @Override
    public void evaluate() throws Throwable {
        ArgumentRule annotation = sMethod.getAnnotation(ArgumentRule.class);

        // 普通测试方法
        if (annotation == null) {
            sMethod.invokeExplosively(sTarget);
            return;
        }

        invokeMethod(annotation.isAsyncTask(), annotation.value());

    }

    protected void invokeMethod(boolean isAsync, ArgumentElement[] elements) throws RUnitTestError {
        // 队列
        RUnitTestCaseQueue queue = new RUnitTestCaseQueue(InstrumentationRegistry.getInstrumentation(), isAsync);

        for (final ArgumentElement element : elements) {

            RUnitTestCaseRunnable runnable = new RUnitTestCaseRunnable(element, errors) {
                @Override
                public void run() {
                    Object[] args = stringArrayToObject(element.value(), this);
                    try {
                        sMethod.invokeExplosively(sTarget, args);
                    } catch (IllegalArgumentException e) {
                        throw new RUnitTestRuntimeException(
                                String.format(Locale.getDefault(), "请检查测试方法的格式：\n方法签名：%s\n方法参数个数/规则参数个数：%d/%d\n1、方法参数必须都是字符串\n2、方法个数是否对上\n 3、最后一个参数必须是RUnitArgumentTestListener<T> listener回调。",
                                        sMethod.toString(),
                                        sMethod.getMethod().getParameterTypes().length,
                                        args.length
                                ), e
                        );
                    } catch (Throwable throwable) {
                        throw new RUnitTestRuntimeException("调用测试方法失败！", throwable);
                    }
                }
            };

            runnable.setTestMethod(sMethod);
            // 调用方法
            queue.put(runnable);

        }

        queue.start();

        testEnd();
    }

    protected void testEnd() throws RUnitTestError {
        if (!errors.isEmpty()) {
            // 抛出异常
            throw new RUnitTestError(errors);
        }
    }

    protected Object[] stringArrayToObject(String[] args, RUnitArgumentTestListener listener) {
        if (args == null) return new Object[]{listener};
        int len = args.length + 1;
        Object[] result = new Object[len];
        System.arraycopy(args, 0, result, 0, args.length);
        result[args.length] = listener;
        return result;
    }
}
