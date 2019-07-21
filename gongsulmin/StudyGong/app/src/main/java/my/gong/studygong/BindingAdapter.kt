package my.gong.studygong

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import my.gong.studygong.adapter.CoinAdapter
import my.gong.studygong.adapter.CoinAdapterByBase
import my.gong.studygong.adapter.CoinListAdapter
import my.gong.studygong.adapter.CoinMarketAdapter
import my.gong.studygong.data.model.Ticker

@BindingAdapter("setItems")
fun <T> RecyclerView.setItems(list: T){
    if (list != null){
        when (adapter) {
            is CoinAdapter -> (adapter as CoinAdapter).refreshData(list as List<Ticker>)
            is CoinMarketAdapter -> (adapter as CoinMarketAdapter).refreshData(list as List<String>)
            is CoinListAdapter -> (adapter as CoinListAdapter).submitList(list as List<Ticker>)
            is CoinAdapterByBase -> (adapter as CoinAdapterByBase).submitList(list as List<Ticker>)
        }
    }
}