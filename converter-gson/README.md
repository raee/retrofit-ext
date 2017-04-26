Gson Converter
==============

A `Converter` which uses [Gson][1] for serialization to and from JSON.

A default `Gson` instance will be created or one can be configured and passed to the
`GsonConverterFactory` to further control the serialization.


Download
--------
[Gradle][3]:
```groovy
compile 'com.squareup.retrofit2:converter-gson-rae:1.0.0'
```

```groovy
allprojects {
    repositories {
        maven { url "http://maven.raeblog.com:8081/repository/maven-public/" }
        jcenter()
    }
}
```

Example
---
```java
 GsonConverterFactory.create(new IGsonResponseBodyConverter() {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Gson gson, TypeAdapter<?> adapter, Type type, Annotation[] annotations) {
        return new MyGsonConverter<>(gson, adapter, type, annotations);
    }
});

private class MyGsonConverter<T> extends GsonResponseBodyConverter<T> {

    MyGsonConverter(Gson gson, TypeAdapter<T> adapter, Type type, Annotation[] annotations) {
        super(gson, adapter, type, annotations);
    }

    @Override
    public T onResponseInterceptor(ResponseBody body, TypeAdapter<T> adapter, Type type, Annotation[] annotations) throws IOException {
        return super.onResponseInterceptor(body, adapter, type, annotations);
    }
}
    
```


 [1]: https://github.com/google/gson
 [2]: https://search.maven.org/remote_content?g=com.squareup.retrofit2&a=converter-gson&v=LATEST
 [3]: http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.squareup.retrofit2%22%20a%3A%22converter-gson%22
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
