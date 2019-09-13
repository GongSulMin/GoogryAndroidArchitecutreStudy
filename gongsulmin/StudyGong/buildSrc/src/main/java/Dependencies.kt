
import Versions.aac_extention_version
import Versions.coroutine_version
import Versions.koin_version
import Versions.okHttp_version
import Versions.recyclerview_version
import Versions.retrofit_coroutine_adpater_version
import Versions.retrofit_version
import Versions.rxandroid_version
import Versions.rxjava_version
import Versions.rxkotlin_version

object Versions {
    val kotlin_version = "1.3.21"
    val retrofit_version = "2.6.0"
    val retrofit_coroutine_adpater_version = "0.9.2"
    val constraintlayout = "1.1.3"
    val okHttp_version = "3.9.1"
    val recyclerview_version = "1.0.0"
    val aac_extention_version = "2.2.0-alpha01"
    val rxandroid_version = "2.1.1"
    val rxjava_version = "2.2.8"
    val rxkotlin_version = "2.3.0"
    val koin_version = "2.0.1"
    val coroutine_version = "1.3.0"
}

object Libs {
 val retrofit =  "com.squareup.retrofit2:retrofit: $retrofit_version"
 val retrofit_gson =  "com.squareup.retrofit2:converter-gson:${retrofit_version}"
 val retrofit_rxjava =  "com.squareup.retrofit2:adapter-rxjava2:${retrofit_version}"
 val retrofit_coroutine_adatper = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${retrofit_coroutine_adpater_version}"
 val okhttp_logging =  "com.squareup.okhttp3:logging-interceptor:${okHttp_version}"
 val okhttp =  "com.squareup.okhttp3:okhttp:${okHttp_version}"
 val recyclerview =  "androidx.recyclerview:recyclerview:${recyclerview_version}"
 val aac_lifecycle_extensions =  "androidx.lifecycle:lifecycle-extensions:${aac_extention_version}"
 val aac_lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${aac_extention_version}"
 val rxandroid =  "io.reactivex.rxjava2:rxandroid:${rxandroid_version}"
 val rxjava =  "io.reactivex.rxjava2:rxjava:${rxjava_version}"
 val rxkotlin =  "io.reactivex.rxjava2:rxkotlin:${rxkotlin_version}"
 val koin_core =  "org.koin:koin-core:${koin_version}"
 val koin_android_viewmodel =  "org.koin:koin-androidx-viewmodel:${koin_version}"
 val koin_android =  "org.koin:koin-android:${koin_version}"
 val koin_test =  "org.koin:koin-test:${koin_version}"
 val coroutine_core =  "org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutine_version}"
 val coroutine_android =  "org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutine_version}"
}

