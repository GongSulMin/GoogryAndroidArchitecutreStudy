package my.gong.studygong.data.source.upbit

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.gong.studygong.data.DataResult
import my.gong.studygong.data.model.Ticker
import my.gong.studygong.data.model.response.UpbitMarketResponse
import my.gong.studygong.data.model.response.UpbitTickerResponse
import my.gong.studygong.data.model.response.toTicker
import my.gong.studygong.data.network.UpbitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpbitRepository(
    val upbitApi: UpbitApi
)
    : UpbitDataSource {


    private var market: String? = null
    private var coinCurrencyList: List<String>? = null

    override fun getTickers(
        tickerCurrency: String,
        success: (List<Ticker>) -> Unit,
        fail: (String) -> Unit
    ) {
        getMarket(
            success = { market ->
                this.market = market
                upbitApi.getTicker(market)
                    .enqueue(object : retrofit2.Callback<List<UpbitTickerResponse>> {
                        override fun onResponse(
                            call: Call<List<UpbitTickerResponse>>,
                            response: Response<List<UpbitTickerResponse>>
                        ) {
                            response.body()?.let { tickerResponse ->
                                success.invoke(
                                    tickerResponse
                                        .filter {
                                            it.market.startsWith(tickerCurrency)
                                        }
                                        .map {
                                            it.toTicker()
                                        }
                                )
                            } ?: fail.invoke("  Response Data is NULL ")
                        }

                        override fun onFailure(call: Call<List<UpbitTickerResponse>>, t: Throwable) {
                            fail.invoke(" 코인 데이터 통신 불가    ")
                        }
                    })
            },
            fail = {
                fail.invoke(it)
            }
        )
    }

    override suspend fun getTickersFlow(tickerCurrency: String): Flow<DataResult<List<Ticker>>> = flow {
        val repoonse = upbitApi.getMarketByCoroutineFlow(getMarketDeffer())
        try {
           val datas = repoonse.body()?.let{
                it.filter {
                    it.market.startsWith(tickerCurrency)
                }
                .map {
                    it.toTicker()
                }
            } ?: run {
               listOf<Ticker>()
            }

           emit(DataResult.Success(datas))

        }catch (e: Exception) {
            e.printStackTrace()
            emit(DataResult.Error(e))
        }
    }

    override suspend fun getCoinCurrencyByCoroutineDeferred(): List<String> {
        val upbitResponse = upbitApi.getMarketByCoroutineDeferred().await()
        return upbitResponse.let {
                    it.map {
                        it.market.substring(0, it.market.indexOf("-"))
                    }
                        .distinct()
                        .toList()
        }
    }

    override fun getCoinCurrency(
        success: (List<String>) -> Unit,
        fail: (String) -> Unit
    ) {
        if (coinCurrencyList == null) {
            upbitApi.getMarket()
                .enqueue(object : Callback<List<UpbitMarketResponse>> {
                    override fun onResponse(
                        call: Call<List<UpbitMarketResponse>>,
                        response: Response<List<UpbitMarketResponse>>
                    ) {
                        response.body()?.let {
                            coinCurrencyList =
                                it.map {
                                    it.market.substring(0, it.market.indexOf("-"))
                                }
                                    .distinct()
                                    .toList()

                            success.invoke(
                                coinCurrencyList!!
                            )
                        }
                    }

                    override fun onFailure(call: Call<List<UpbitMarketResponse>>, t: Throwable) {
                        fail.invoke(" 코인 정보 불가    ")
                    }
                })
        } else {
            success.invoke(coinCurrencyList!!)
        }
    }


    override fun getCoinCurrencyByRx(
        success: (List<String>) -> Unit,
        fail: (String) -> Unit)
        : Disposable {

        return upbitApi.getMarketByRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        success.invoke(
                            it.map {
                                it.market.substring(0 , it.market.indexOf("-"))
                            }
                                .distinct()
                                .toList()
                        )
                        Log.e("RX", "" + it.map {
                                                        it.market.substring(0, it.market.indexOf("-"))
                                                    }
                                                    .distinct()
                                                    .toList()
                        )
                    } ,
                    {
                        fail.invoke(it.toString())

                    }
                )

    }

    override fun getDetailTickers(
        tickerSearch: String,
        success: (List<Ticker>) -> Unit,
        fail: (String) -> Unit
    ) {
        getMarket(
            success = { market ->
                this.market = market
                upbitApi.getTicker(market)
                    .enqueue(object : retrofit2.Callback<List<UpbitTickerResponse>> {
                        override fun onResponse(
                            call: Call<List<UpbitTickerResponse>>,
                            response: Response<List<UpbitTickerResponse>>
                        ) {
                            response.body()?.let { tickerResponse ->

                                tickerResponse
                                    .filter {
                                        it.market.endsWith(tickerSearch, ignoreCase = true)
                                    }
                                    .map {
                                        it.toTicker()
                                    }.let {
                                        if (it.isNotEmpty()) {
                                            success.invoke(
                                                it
                                            )
                                        } else {
                                            fail.invoke("  검색 결과 없음 ")
                                        }
                                    }
                            } ?: fail.invoke("  Response Data is NULL ")
                        }

                        override fun onFailure(call: Call<List<UpbitTickerResponse>>, t: Throwable) {
                            fail.invoke(" 코인 데이터 통신 불가    ")
                        }
                    })
            },
            fail = {
                fail.invoke(it)
            }
        )
    }

    private suspend fun getMarketDeffer():String{
        val reponse = upbitApi.getMarketCoroutine().await()
        val data = reponse.let {
             it.joinToString(","){
                it.market
            }
        }
        return  data
    }


    private fun getMarket(
        success: (String) -> Unit,
        fail: (String) -> Unit
    ) {
        if (market == null) {
            upbitApi.getMarket()
                .enqueue(object : retrofit2.Callback<List<UpbitMarketResponse>> {
                    override fun onResponse(
                        call: Call<List<UpbitMarketResponse>>,
                        response: Response<List<UpbitMarketResponse>>
                    ) {
                        response.body()?.let {
                            success.invoke(
                                it.joinToString(",") {
                                    it.market
                                }
                            )
                        }
                    }

                    override fun onFailure(call: Call<List<UpbitMarketResponse>>, t: Throwable) {
                        fail.invoke(" 마켓 데이터 통신 불가   ")
                    }
                })
        } else {
            success.invoke(market!!)
        }
    }

}


