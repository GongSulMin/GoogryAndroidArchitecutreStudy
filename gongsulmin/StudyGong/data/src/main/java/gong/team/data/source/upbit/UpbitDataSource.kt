package gong.team.data.source.upbit

import gong.team.data.DataResult
import gong.team.data.model.Ticker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow

interface UpbitDataSource {

    suspend fun getDetailTickersByCoroutineDeferred(tickerSearch: String): DataResult<List<Ticker>>

    fun CoroutineScope.getTickersChannel(tickerCurrency: String): ReceiveChannel<DataResult<List<Ticker>?>>

    fun getTickersFlow(tickerCurrency: String): Flow<DataResult<List<Ticker>?>>

    fun getCoinMarket(): Flow<DataResult<List<String>>>

}
