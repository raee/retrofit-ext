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

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snap].



 [1]: https://github.com/google/gson
 [2]: https://search.maven.org/remote_content?g=com.squareup.retrofit2&a=converter-gson&v=LATEST
 [3]: http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.squareup.retrofit2%22%20a%3A%22converter-gson%22
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
