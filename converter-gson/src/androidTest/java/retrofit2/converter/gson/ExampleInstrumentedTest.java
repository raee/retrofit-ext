package retrofit2.converter.gson;

import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {

        GsonConverterFactory.create(new IGsonResponseBodyConverter() {
            @Override
            public Converter<ResponseBody, ?> responseBodyConverter(Gson gson, TypeAdapter<?> adapter, Type type, Annotation[] annotations) {
                return new MyGsonConverter<>(gson, adapter, type, annotations);
            }
        });

    }


    private class MyGsonConverter<T> extends GsonResponseBodyConverter<T> {

        MyGsonConverter(Gson gson, TypeAdapter<T> adapter, Type type, Annotation[] annotations) {
            super(gson, adapter, type, annotations);
        }

        @Override
        public T onResponseInterceptor(ResponseBody body, TypeAdapter<T> adapter, Type type, Annotation[] annotations) throws IOException {
            return super.onResponseInterceptor(body, adapter, type, annotations);
        }
    }
}
