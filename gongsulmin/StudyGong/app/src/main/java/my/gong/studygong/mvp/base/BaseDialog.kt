package my.gong.studygong.mvp.base

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseDialog(private val layoutRes: Int) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setPosition()
    }

    fun setPosition(): Unit {
        dialog.window!!.attributes.apply {
            width = resources.displayMetrics.widthPixels / 10 * 8
        }
    }
}