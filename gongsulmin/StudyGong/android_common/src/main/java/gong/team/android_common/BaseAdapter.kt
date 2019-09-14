package gong.team.android_common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<T>(diffCallback: DiffUtil.ItemCallback<T> , private val itemVarId: Int)
    : ListAdapter<T, BaseViewHolder<T>>(diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val itemBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context) ,
            viewType ,
            parent ,
            false
        )
        return gong.team.android_common.BaseViewHolder(itemBinding, itemVarId)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }

}