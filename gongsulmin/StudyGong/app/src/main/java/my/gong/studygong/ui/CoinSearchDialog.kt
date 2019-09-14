package my.gong.studygong.ui

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import my.gong.studygong.R
import my.gong.studygong.adapter.CoinAdapter
import gong.team.android_common.BaseDialog
import my.gong.studygong.databinding.DialogCoinDetailBinding
import my.gong.studygong.viewmodel.CoinViewModel

/**
 *          Coin 검색 다이얼로그
 *
 */
class CoinSearchDialog
    : gong.team.android_common.BaseDialog<DialogCoinDetailBinding>(R.layout.dialog_coin_detail) {

    private val coinViewModel: CoinViewModel by sharedViewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.coinViewModel = coinViewModel

        viewDataBinding.recyclerviewCoinDetail.adapter = CoinAdapter()

        coinViewModel.loadTickerSearchResult()

        coinViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

    }
}