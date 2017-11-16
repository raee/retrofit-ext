package com.github.raee.runit;

import android.util.Log;

import com.github.raee.runit.argument.ArgumentElement;
import com.github.raee.runit.argument.ArgumentRule;
import com.github.raee.runit.argument.ArgumentTestClass;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.annotation.Annotation;

/**
 * 参数化测试例子
 * Created by ChenRui on 2017/1/12 0012 15:52.
 */
@RunWith(ArgumentRUnit.class)
@ArgumentTestClass(configPath = "testRule.json")
public class ArgumentRUnitDemoTest {

    @Test
    @ArgumentRule.configMethod
    public void testArgument(String word, RUnitArgumentTestListener<String> listener) {
        Log.d("rae", "say " + word);
        listener.finish();
    }

    @Test
    public void testAA(String nasme){

    }

    @Test
    public void testRae() throws IllegalAccessException, InstantiationException {
        ArgumentElement element = new ArgumentElement() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Annotation.class;
            }

            @Override
            public String name() {
                return "rae";
            }

            @Override
            public String[] value() {
                return new String[0];
            }

            @Override
            public String errorMessage() {
                return null;
            }

            @Override
            public String successMessage() {
                return null;
            }
        };
        Log.i("rae", "666666666" + element.name());
    }
}
