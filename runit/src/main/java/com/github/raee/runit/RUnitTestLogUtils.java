package com.github.raee.runit;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Test case Log helper
 * Created by ChenRui on 2017/1/5 0005 13:54.
 */
public class RUnitTestLogUtils {

    /**
     * 打印对象
     *
     * @param tag log tag
     * @param obj object
     */
    public static void printfObject(String tag, Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Log.d(tag, field.getName() + " = " + field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 打印List对象
     */
    public static <T> void printfList(String tag, List<T> data) {
        for (T t : data) {
            printfObject(tag, t);
            Log.d(tag, "---------------------------");
        }
    }


    /**
     * 输出泛型对象
     *
     * @param data
     */
    public static <T> void print(String tag, T data) {
        if (data == null) {
            Log.d(tag, "打印对象为空");
            return;
        }
        Log.d(tag, "=====================================================");
        Log.d(tag, "打印对象信息：");
        Log.d(tag, "--------------");
        if (data instanceof Void) {
            Log.d(tag, "无打印信息，对象为Void 类型。");
        }
        if (data instanceof List) {
            printfList(tag, (List) data);
        } else if (data.getClass().isArray()) {
            printfList(tag, Arrays.asList(data));
        } else {
            printfObject(tag, data);
        }
        Log.d(tag, "=====================================================");
    }

}
