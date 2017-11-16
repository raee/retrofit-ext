package com.github.raee.runit;

/**
 * 参数化测试回调
 * Created by ChenRui on 2017/1/11 0011 11:58.
 */
public interface RUnitArgumentTestListener<T> {
    /**
     * 测试成功调用
     *
     * @param data 实体数据
     */
    void onApiSuccess(T data);

    /**
     * 测试成功调用
     *
     * @return 如果返回真，只做下一步处理
     */
    void onApiError(int error, String msg);

    /**
     * 测试完成
     */
    void finish();
}
