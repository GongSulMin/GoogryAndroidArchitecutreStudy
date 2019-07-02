package my.gong.studygong.data.source.upbit

import io.reactivex.disposables.Disposable
import my.gong.studygong.data.model.Ticker

interface UpbitDataSource {
    fun getTickers(
        tickerCurrency: String,
        success: (List<Ticker>) -> Unit,
        fail: (String) -> Unit
    )

    fun getDetailTickers(
        tickerSearch: String,
        success: (List<Ticker>) -> Unit,
        fail: (String) -> Unit
    )

    fun getCoinCurrency(
        success: (List<String>) -> Unit,
        fail: (String) -> Unit
    )

    fun getCoinCurrencyByRx(
        success: (List<String>) -> Unit,
        fail: (String) -> Unit
    ): Disposable
}