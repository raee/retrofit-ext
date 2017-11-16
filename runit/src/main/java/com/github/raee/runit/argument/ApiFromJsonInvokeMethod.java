package com.github.raee.runit.argument;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.text.TextUtils;
import android.util.Log;

import com.github.raee.runit.RUnitTestError;
import com.github.raee.runit.RUnitTestRuntimeException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * API 方法调用
 * 从文件中获取参数信息
 * Created by ChenRui on 2017/1/11 0011 11:04.
 */
public class ApiFromJsonInvokeMethod extends ApiInvokeMethod {

    private final ArgumentRule.configMethod mRuleAnnotation;
    private String mConfigPath;
    private Context mContext;

    public ApiFromJsonInvokeMethod(TestClass testClass, FrameworkMethod testMethod, Object target) {
        super(testClass, testMethod, target);
        mContext = InstrumentationRegistry.getContext();
        ArgumentTestClass testClassAnnotation = testClass.getAnnotation(ArgumentTestClass.class);
        if (testClassAnnotation != null) {
            mConfigPath = testClassAnnotation.configPath();
        }
        mRuleAnnotation = testMethod.getAnnotation(ArgumentRule.configMethod.class);
        if (mRuleAnnotation == null)
            throw new RuntimeException(testClass.getName() + "没有标志ArgumentRule.configMethod.class");
    }

    @Override
    public void evaluate() throws Throwable {
        // 普通测试方法
        if (sMethod.getAnnotation(ArgumentRule.configMethod.class) == null) {
            sMethod.invokeExplosively(sTarget);
            return;
        }

        String path;
        String methodName = mRuleAnnotation.value();
        if (TextUtils.isEmpty(methodName)) {
            methodName = sMethod.getName();
            Log.d("rae", "method = " + methodName);
        }
        if (mConfigPath == null) {
            path = mRuleAnnotation.path();
        } else {
            path = mConfigPath;
        }

        try {
            InputStream inputStream = mContext.getAssets().open(path);
            BufferedInputStream stream = new BufferedInputStream(inputStream);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[128];
            while ((len = stream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            String json = out.toString();
            stream.close();
            out.close();

            JSONObject obj = new JSONObject(json);
            if (obj.has(methodName)) {
                List<ArgumentElement> result = getArgumentElement(obj.get(methodName).toString().trim());
                ArgumentElement[] elements = new ArgumentElement[result.size()];
                elements = result.toArray(elements);
                invokeMethod(true, elements);
            } else {
                throw new RUnitTestError(String.format("配置文件中没有找到测试方法：%s，文件：%s", methodName, path));
            }

        } catch (JSONException e) {
            throw new RUnitTestRuntimeException("配置文件解析错误：" + path, e);
        } catch (IOException e) {
            throw new RUnitTestRuntimeException("读取配置文件发出异常，请检查assets目录下的是否存在：" + path, e);
        }

    }


    private List<ArgumentElement> getArgumentElement(String json) throws JSONException {
        if (!json.startsWith("[")) {
            throw new RUnitTestRuntimeException("方法必须是数组形式，比如 \"method\":[]");
        }

        JSONArray array = new JSONArray(json);
        int len = array.length();
        List<ArgumentElement> res = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            final JSONObject o = array.getJSONObject(i);
            ArgumentElement e = new ArgumentElement() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return ArgumentElement.class;
                }

                @Override
                public String name() {
                    if (o.has("name")) {
                        try {
                            return o.getString("name");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    return "";
                }

                @Override
                public String[] value() {
                    try {
                        if (o.has("args")) {
                            JSONArray args = o.getJSONArray("args");
                            String[] value = new String[args.length()];
                            for (int a = 0; a < value.length; a++) {
                                value[a] = args.get(a).toString();
                            }
                            return value;
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    return null;
                }

                @Override
                public String errorMessage() {
                    try {
                        if (o.has("errorMessage")) {
                            return o.getString("errorMessage");
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    return "";
                }

                @Override
                public String successMessage() {
                    try {
                        if (o.has("successMessage")) {
                            return o.getString("successMessage");
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    return "";
                }
            };
            res.add(e);
        }

        return res;
    }


}
