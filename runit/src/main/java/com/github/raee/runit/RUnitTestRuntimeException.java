package com.github.raee.runit;

/**
 * 测试运行时异常
 * Created by ChenRui on 2017/1/5 0005 15:36.
 */
public class RUnitTestRuntimeException extends RuntimeException {

    public RUnitTestRuntimeException() {
    }

    public RUnitTestRuntimeException(Throwable cause) {
        super(cause);
    }

    public RUnitTestRuntimeException(String message) {
        super(message);
    }

    public RUnitTestRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
