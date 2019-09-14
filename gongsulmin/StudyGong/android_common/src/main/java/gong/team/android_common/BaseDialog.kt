package gong.team.android_common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialog<B : ViewDataBinding>(private val layoutRes: Int) : DialogFragment() {

    protected lateinit var viewDataBinding: B
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(
            inflater,
            layoutRes,
            container,
            false
        )
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpDialog()
    }

    private fun setUpDialog() {
        dialog?.window!!.attributes.apply {
            width = resources.displayMetrics.widthPixels / 10 * 8
        }
    }
}