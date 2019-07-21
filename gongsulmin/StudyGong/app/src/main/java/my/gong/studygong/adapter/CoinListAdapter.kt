package my.gong.studygong.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import my.gong.studygong.R
import my.gong.studygong.data.model.Ticker
import my.gong.studygong.databinding.ItemTickerBinding

class CoinListAdapter
    : ListAdapter<Ticker, CoinListAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Ticker>() {
    override fun areItemsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
        return oldItem.market == newItem.market
    }

    override fun areContentsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
        return oldItem.tradePrice == newItem.tradePrice
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<ItemTickerBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_ticker,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewDataBinding.item = getItem(position)
    }

    class ViewHolder(val viewDataBinding: ItemTickerBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

}

