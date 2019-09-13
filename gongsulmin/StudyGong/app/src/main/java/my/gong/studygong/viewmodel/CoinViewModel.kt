package my.gong.studygong.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import my.gong.studygong.SingleLiveEvent
import my.gong.studygong.data.DataResult
import my.gong.studygong.data.model.Ticker
import my.gong.studygong.data.source.upbit.UpbitDataSource

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

    var job = SupervisorJob()

    fun loadCoin() {
        loadTickerList(baseCurrency.value!!)
    }

    fun onStop() {
    }

    fun loadTickerList(currency: String) {
        viewModelScope.coroutineContext
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    upbitRepository.getTickersFlow(currency).collect {
                        when(it) {
                            is DataResult.Success -> _tickerList.postValue(it.data)
                            else -> println(" EROOR OR LOADING ")
                        }
                    }
                }
            }

        /**
         *
         *          Channel 
         */
//        viewModelScope.launch {
//            with(upbitRepository) {
//                getTickersChannel(currency).consumeEach {
//                    if (it is DataResult.Success) {
//                        _tickerList.value = it.data
//                    }else {
//                        Log.e("코루틴" , " 코루틴 실패다 이새키야 ")
//                    }
//                }
//            }
//        }
    }

    fun loadTickerSearchResult() {

        viewModelScope.launch {
            if (searchTicker.value!!.isNotEmpty()) {
                withContext(Dispatchers.Main){

                    val dataResult = upbitRepository.getDetailTickersByCoroutineDeferred(searchTicker.value!!)

                    if (dataResult is DataResult.Success && dataResult.data.isNotEmpty()) {
                        _searchTickerList.value = dataResult.data
                    } else {
                        errorMessage.value = "검색한 코인은 찾을수 없습니다!"
                    }
                }
                
            } else {
                errorMessage.value = "검색한 코인은 찾을수 없습니다!"
            }
        }
    }

     fun loadBaseCurrency() {
         viewModelScope.launch {
             withContext(Dispatchers.IO) {
                 upbitRepository. getCoinMarket().collect {
                     when(it) {
                         is DataResult.Success -> {
                             _baseCurrencyList.postValue(it.data)
                         }
                         else -> println(" ERROR or Loading ")
                     }
                 }
             }
         }
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