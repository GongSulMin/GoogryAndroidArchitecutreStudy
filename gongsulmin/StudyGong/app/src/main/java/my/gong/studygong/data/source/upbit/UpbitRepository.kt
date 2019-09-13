package my.gong.studygong.data.source.upbit

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import my.gong.studygong.data.DataResult
import my.gong.studygong.data.model.Ticker
import my.gong.studygong.data.model.response.UpbitTickerResponse
import my.gong.studygong.data.model.response.toTicker
import my.gong.studygong.data.network.UpbitApi


class UpbitRepository(
    val upbitApi: UpbitApi
) : UpbitDataSource {

    private var market: String? = null
    private var coinCurrencyList: List<String>? = null

    override fun getTickersFlow(tickerCurrency: String): Flow<DataResult<List<Ticker>>> =
        flow<DataResult<List<Ticker>>> {
            // while delay wk
            while (true) {
                val response = upbitApi.getMarketByCoroutineFlow(getMarketDeffer())

                emit(
                    DataResult.Success(
                        response.body()?.let {
                            it.filter {
                                it.market.startsWith(tickerCurrency)
                            }
                                .map {
                                    it.toTicker()
                                }
                        } ?: run {
                            emptyList<Ticker>()
                        }
                    )
                )
                delay(1000L)
            }

        }
            .conflate()
            .catch {
                emit(DataResult.Error(Exception(it)))
            }

    private suspend fun getMarketDeffer(): String {
        return upbitApi.getMarketCoroutineAsync().await().joinToString(",") { it.market }
    }

    override fun getCoinMarket(): Flow<DataResult<List<String>>> = flow<DataResult<List<String>>> {
        val response = upbitApi.getMarketList()

        if (response.isSuccessful) {
            emit(
                DataResult.Success(
                    response.body()!!                                             
                        .map {
                            it.market.substringBefore("-")
                        }
                        .distinct()
                        .toList()
                )
            )
        }
    }
        .catch {
            Exception(it).printStackTrace()
            emit(DataResult.Error(Exception(it)))
        }

    override suspend fun getDetailTickersByCoroutineDeferred(tickerSearch: String): DataResult<List<Ticker>> {
        return try {
            val response = upbitApi.getDetailTickerByCoroutineDeferredAsync(getMarketDeffer()).await()
            DataResult.Success(
                response.let {
                    it.filter {
                        it.market.endsWith(tickerSearch, ignoreCase = true)
                    }
                        .map(UpbitTickerResponse::toTicker)
                }
            )
        } catch (e: java.lang.Exception) {
            DataResult.Error(e)
        }
    }

    override fun CoroutineScope.getTickersChannel(tickerCurrency: String): ReceiveChannel<DataResult<List<Ticker>?>> =
        produce {
            while (true) {
                val repoonse = upbitApi.getMarketByCoroutineFlow(getMarketDeffer())
                try {
                    val datas =

                    send(
                        DataResult.Success(
                            repoonse.body()?.let {
                                it.filter {
                                    it.market.startsWith(tickerCurrency)
                                }
                                    .map {
                                        it.toTicker()
                                    }
                            } ?: run {
                                listOf<Ticker>()
                            }
                        )
                    )

                } catch (e: Exception) {
                    e.printStackTrace()
                    send(DataResult.Error(e))
                }
                delay(1000L)
            }
        }


}


