package retrofit2.converter.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * gson converter interceptor
 * Created by ChenRui on 2017/4/25 0025 14:01.
 */
public interface IGsonResponseBodyConverter {

    Converter<ResponseBody, ?> responseBodyConverter(Gson gson, TypeAdapter<?> adapter, Type type, Annotation[] annotations);

}
