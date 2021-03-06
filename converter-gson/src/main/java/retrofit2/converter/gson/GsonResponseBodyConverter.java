/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package retrofit2.converter.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    protected final Gson gson;
    protected final TypeAdapter<T> adapter;
    protected final Type type;
    protected final Annotation[] annotations;

    public GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, Type type, Annotation[] annotations) {
        this.gson = gson;
        this.adapter = adapter;
        this.type = type;
        this.annotations = annotations;
    }


    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            return onResponseInterceptor(value, adapter, type, annotations);
        } finally {
            value.close();
        }
    }

    public T onResponseInterceptor(ResponseBody body, TypeAdapter<T> adapter, Type type, Annotation[] annotations) throws IOException {
        return convert(gson.newJsonReader(body.charStream()));
    }

    protected T convert(JsonReader jsonReader) throws IOException {
        return adapter.read(jsonReader);
    }
}
