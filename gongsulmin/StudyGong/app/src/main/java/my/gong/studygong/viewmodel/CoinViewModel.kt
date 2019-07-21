package my.gong.studygong.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import my.gong.studygong.SingleLiveEvent
import my.gong.studygong.data.model.Ticker
import my.gong.studygong.data.source.upbit.UpbitDataSource
import java.util.*

class CoinViewModel(
    private val upbitRepository: UpbitDataSource
) : ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    private val _tickerList = MutableLiveData<List<Ticker>>()
    val tickerList: LiveData<List<Ticker>>
        get() = _tickerList

    private val _searchTickerList = MutableLiveData<List<Ticker>>()
    val searchTickerList: LiveData<List<Ticker>>
        get() = _searchTickerList

    private val _baseCurrencyList = MutableLiveData<List<String>>()
    val baseCurrencyList: LiveData<List<String>>
        get() = _baseCurrencyList

    var dismissCoinMarketDialog = SingleLiveEvent<Any>()

    private val _showCoinSearchDialog = SingleLiveEvent<String>()
    val showCoinSearchDialog: LiveData<String>
        get() = _showCoinSearchDialog

    val showCoinMarketDialog = SingleLiveEvent<Any>()

    val errorMessage = SingleLiveEvent<String>()

    private val _baseCurrency = MutableLiveData<String>("KRW")
    val baseCurrency: LiveData<String>
        get() = _baseCurrency

    val searchTicker = MutableLiveData<String>("")

    private var timer: Timer = Timer()

    fun loadCoin() {
        timer.cancel()
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                loadTickerList(baseCurrency.value!!)
            }
        }, 0, REPEAT_INTERVAL_MILLIS)
    }

    fun onStop() {
        timer.cancel()
    }

    fun loadTickerList(currency: String) {
        upbitRepository.getTickers(
            tickerCurrency = currency,
            success = {
                _tickerList.value = it
            },
            fail = {
                errorMessage.value = it
            }
        )
    }

    fun loadTickerSearchResult() {
        if (searchTicker.value!!.isNotEmpty()) {
            upbitRepository.getDetailTickers(
                tickerSearch = searchTicker.value!!,
                success = {
                    _searchTickerList.value = it
                },
                fail = {
                    errorMessage.value = it
                }
            )
        } else {
            errorMessage.value = "검색한 코인은 찾을수 없습니다!"
        }
    }

    fun loadBaseCurrency() {
        compositeDisposable.add(
            upbitRepository.getCoinCurrencyByRx(
                success = {
                    _baseCurrencyList.value = it
                },
                fail = {
                    errorMessage.value = it
                }
            )
        )
    }

    fun showCoinMarketDialog() {
        showCoinMarketDialog.call()
    }

    fun showCoinSearchDialog(searchTicker: String) {
        _showCoinSearchDialog.value = searchTicker
    }

    fun selectBaseCurrnecy(selectBaseCurrency: String) {
        _baseCurrency.value = selectBaseCurrency
        loadCoin()
        dismissCoinMarketDialog.call()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


    companion object {
        const val REPEAT_INTERVAL_MILLIS = 1000L
    }
}