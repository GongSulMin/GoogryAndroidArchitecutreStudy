package my.gong.studygong.data.source.upbit

import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import my.gong.studygong.data.DataResult
import my.gong.studygong.data.model.Ticker

interface UpbitDataSource {
//    fun getTickers(
//        tickerCurrency: String,
//        success: (List<Ticker>) -> Unit,
//        fail: (String) -> Unit
//    )
//
    fun getDetailTickers(
        tickerSearch: String,
        success: (List<Ticker>) -> Unit,
        fail: (String) -> Unit
    )
//
//    fun getCoinCurrency(
//        success: (List<String>) -> Unit,
//        fail: (String) -> Unit
//    )
//
//    fun getCoinCurrencyByRx(
//        success: (List<String>) -> Unit,
//        fail: (String) -> Unit
//    ): Disposable

    suspend fun getCoinCurrencyByCoroutineDeferred(): List<String>

    suspend fun getDetailTickersByCoroutineDeferred(tickerSearch: String): DataResult<List<Ticker>>

    fun CoroutineScope.getTickersChannel(tickerCurrency: String): ReceiveChannel<DataResult<List<Ticker>?>>

    fun getTickersFlow(tickerCurrency: String): Flow<DataResult<List<Ticker>?>>



}
