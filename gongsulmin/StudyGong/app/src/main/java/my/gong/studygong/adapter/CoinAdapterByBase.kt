package my.gong.studygong.adapter

import androidx.recyclerview.widget.DiffUtil
import my.gong.studygong.R
import my.gong.studygong.base.BaseAdapter
import my.gong.studygong.data.model.Ticker

class CoinAdapterByBase
    : BaseAdapter<Ticker>(DiffCallback()) {

    class DiffCallback: DiffUtil.ItemCallback<Ticker>(){
        override fun areItemsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
            return oldItem.market == newItem.market
        }

        override fun areContentsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
            return oldItem.tradePrice == newItem.tradePrice
        }
    }

    override fun getItemViewType(position: Int) = R.layout.item_ticker

}