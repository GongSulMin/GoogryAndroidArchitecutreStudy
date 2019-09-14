package my.gong.studygong.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import gong.team.data.model.Ticker
import my.gong.studygong.R
import my.gong.studygong.databinding.ItemTickerBinding

class CoinAdapter
    : RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    private val coinList: MutableList<Ticker> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<ItemTickerBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_ticker,
            parent,
            false
        )
        return ViewHolder(viewDataBinding)
    }

    override fun getItemCount() = coinList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewDataBinding.item = coinList[position]
    }

    fun refreshData(coinList: List<Ticker>) {
        val diffCallback: CoinDiffCallback = CoinDiffCallback(this.coinList , coinList)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)

        this.coinList.run {
            clear()
            addAll(coinList)
        }

        Handler(Looper.getMainLooper()).post{
            diffResult.dispatchUpdatesTo(this)
        }

        this.coinList.run {
            clear()
            addAll(coinList)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(val viewDataBinding: ItemTickerBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

    class CoinDiffCallback(newList: List<Ticker>, oldList: List<Ticker>) : DiffUtil.Callback() {

        private var oldCoinList: List<Ticker> = oldList
        private var newCoinList: List<Ticker> = newList

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCoinList[oldItemPosition].market == newCoinList[oldItemPosition].market
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCoinList[oldItemPosition].tradePrice == newCoinList[oldItemPosition].tradePrice
        }

        override fun getOldListSize(): Int {
            return oldCoinList.size
        }

        override fun getNewListSize(): Int {
            return newCoinList.size
        }

    }

}