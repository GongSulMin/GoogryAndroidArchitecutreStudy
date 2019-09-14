package my.gong.studygong.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import my.gong.studygong.R
import my.gong.studygong.adapter.CoinMarketAdapter
import gong.team.android_common.BaseDialog
import my.gong.studygong.databinding.DialogCoinMarketBinding
import my.gong.studygong.viewmodel.CoinViewModel

/**
 *          Coin Market 필터 다이얼로그
 *
 */
class CoinMarketDialog
    : gong.team.android_common.BaseDialog<DialogCoinMarketBinding>(R.layout.dialog_coin_market) {

    private val coinViewModel: CoinViewModel by sharedViewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.coinViewModel = coinViewModel

        coinViewModel.loadBaseCurrency()

        coinViewModel.dismissCoinMarketDialog.observe(this, Observer {
            dismiss()
        })

        viewDataBinding.recyclerviewCoinMarket.adapter =
            CoinMarketAdapter(
                coinViewModel
            )
    }

}