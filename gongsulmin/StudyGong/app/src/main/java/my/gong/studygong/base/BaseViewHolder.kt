package my.gong.studygong.base

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<T>(private val itemBinding: ViewDataBinding)
    : RecyclerView.ViewHolder(itemBinding.root){

    fun bind(item: T){
        itemBinding.setVariable(BR.item , item)
        itemBinding.executePendingBindings()
    }

}

