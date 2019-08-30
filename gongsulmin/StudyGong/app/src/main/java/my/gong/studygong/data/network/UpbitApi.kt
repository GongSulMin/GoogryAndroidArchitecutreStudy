package my.gong.studygong.data.network

import io.reactivex.Single
import kotlinx.coroutines.Deferred
import my.gong.studygong.data.model.response.UpbitMarketResponse
import my.gong.studygong.data.model.response.UpbitTickerResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UpbitApi {

    @GET("v1/market/all")
    fun getMarketCoroutineAsync(): Deferred<List<UpbitMarketResponse>>

    @GET("v1/market/all")
    suspend fun getMarketList(): Response<List<UpbitMarketResponse>>

    @GET("v1/ticker")
    suspend fun getMarketByCoroutineFlow(@Query(value = "markets", encoded = true) tickers: String): Response<List<UpbitTickerResponse>>

    @GET("v1/ticker")
    fun getDetailTickerByCoroutineDeferredAsync(@Query(value = "markets", encoded = true) tickers: String): Deferred<List<UpbitTickerResponse>>


    companion object {
        val BASE_URL = " https://api.upbit.com/"
    }
}