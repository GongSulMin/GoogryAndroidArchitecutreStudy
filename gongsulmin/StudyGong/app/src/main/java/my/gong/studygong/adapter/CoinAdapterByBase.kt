package my.gong.studygong.adapter

import androidx.recyclerview.widget.DiffUtil
import gong.team.android_common.BaseAdapter
import gong.team.data.model.Ticker
import my.gong.studygong.BR
import my.gong.studygong.R

class CoinAdapterByBase
    : BaseAdapter<Ticker>(DiffCallback() , BR.item) {

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