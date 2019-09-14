package gong.team.android_common

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<T>(
    private val itemBinding: ViewDataBinding ,
    private val itemVarId: Int
)
    : RecyclerView.ViewHolder(itemBinding.root){

    fun bind(item: T){
        itemBinding.setVariable(itemVarId , item)
        itemBinding.executePendingBindings()
    }

}

