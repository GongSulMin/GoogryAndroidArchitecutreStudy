package my.gong.studygong.data.source.upbit

import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import my.gong.studygong.data.DataResult
import my.gong.studygong.data.model.Ticker

interface UpbitDataSource {

    suspend fun getDetailTickersByCoroutineDeferred(tickerSearch: String): DataResult<List<Ticker>>

    fun CoroutineScope.getTickersChannel(tickerCurrency: String): ReceiveChannel<DataResult<List<Ticker>?>>

    fun getTickersFlow(tickerCurrency: String): Flow<DataResult<List<Ticker>?>>

    fun getCoinMarket(): Flow<DataResult<List<String>>>

}
