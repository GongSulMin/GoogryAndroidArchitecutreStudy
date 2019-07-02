package my.gong.studygong.data.network

import io.reactivex.Single
import my.gong.studygong.data.model.response.UpbitMarketResponse
import my.gong.studygong.data.model.response.UpbitTickerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UpbitApi {

    @GET("v1/market/all")
    fun getMarket(): Call<List<UpbitMarketResponse>>

    @GET("v1/market/all")
    fun getMarketByRx(): Single<List<UpbitMarketResponse>>

    @GET("v1/ticker")
    fun getTicker(@Query(value = "markets", encoded = true) tickers: String): Call<List<UpbitTickerResponse>>

//    @GET("v1/ticker")
//    fun getTickerByRx(@Query(value = "markets", encoded = true) tickers: String): Observable<List<UpbitTickerResponse>>


    companion object {
        val BASE_URL = " https://api.upbit.com/"
    }
}