package my.gong.studygong.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import my.gong.studygong.data.network.UpbitApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder().baseUrl(UpbitApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .client(
//                OkHttpClient.Builder().addInterceptor(
//                    HttpLoggingInterceptor()
//                        .apply {
//                            level = HttpLoggingInterceptor.Level.BODY
//                        }
//                )
//                    .build()
//            )
            .build()
            .create(UpbitApi::class.java)
    }
}