package my.gong.studygong.data.source.upbit

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import my.gong.studygong.data.model.Ticker
import my.gong.studygong.data.model.response.UpbitMarketResponse
import my.gong.studygong.data.model.response.UpbitTickerResponse
import my.gong.studygong.data.network.UpbitApi
import retrofit2.Call
import retrofit2.Response

class UpbitRepository(
    val upbitApi: UpbitApi
)
    : UpbitDataSource {

    private var market: String? = null
    private var coinCurrencyList: List<String>? = null

    override fun getCoinCurrency(
        success: (List<String>) -> Unit,
        fail: (String) -> Unit
    ) {
        if (coinCurrencyList == null) {
            upbitApi.getMarket()
                .enqueue(object : retrofit2.Callback<List<UpbitMarketResponse>> {
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
                                        Ticker(
                                            it.market,
                                            String.format("%.0f", it.tradePrice),
                                            String.format("%4.2f", it.changeRate * 100),
                                            String.format("%.5f", it.accTradePrice24h)
                                        )
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
                                            Ticker(
                                                it.market,
                                                String.format("%.0f", it.tradePrice),
                                                String.format("%4.2f", it.changeRate * 100),
                                                String.format("%.5f", it.accTradePrice24h)
                                            )
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


