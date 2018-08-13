# Common
mvp base repositorty

# Project Gradle Config
allprojects {
    repositories {
        ....
        maven{ url 'https://jitpack.io'}
    }
}


# Module Gradle Config
dependencies{
      implementation 'com.github.jasonwang18.common:com_view:1.0.0'
      implementation 'com.github.jasonwang18.common:com_router:1.0.0'
      implementation 'com.github.jasonwang18.common:com_http:1.0.0'
      implementation 'com.github.jasonwang18.common:annotation:1.0.0'
      annotationProcessor 'com.github.jasonwang18.common:apt:1.0.0@jar'
      implementation 'com.android.support:appcompat-v7:27.1.1'
      implementation 'com.android.support:recyclerview-v7:27.1.1'
      implementation 'com.android.support:design:27.1.1'
      implementation 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
      implementation 'com.squareup.okhttp3:okhttp:3.9.0'
      implementation 'com.jakewharton:disklrucache:2.0.2'
      implementation 'io.reactivex.rxjava2:rxandroid:2.0.0'
      implementation 'io.reactivex.rxjava2:rxjava:2.1.3'
      implementation 'com.squareup.retrofit2:retrofit:2.3.0'
      implementation 'com.squareup.retrofit2:adapter-rxjava2:2.2.0'
      implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
      implementation 'com.squareup.okhttp3:logging-interceptor:3.9.0'
      implementation 'com.google.code.gson:gson:2.8.1'
      implementation 'org.greenrobot:greendao:3.2.2' // add library
    }
