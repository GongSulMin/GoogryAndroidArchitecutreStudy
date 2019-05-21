package my.gong.studygong.mvp.view

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_coin_market.*
import my.gong.studygong.R
import my.gong.studygong.mvp.Injection
import my.gong.studygong.mvp.adapter.CoinMarketAdapter
import my.gong.studygong.mvp.base.BaseDialog
import my.gong.studygong.mvp.presenter.coinmarketlist.CoinMarketContract
import my.gong.studygong.mvp.presenter.coinmarketlist.CoinMarketPresenter

/**
 *          Coin Market 필터 다이얼로그
 *
 */
class CoinMarketDialog
    : BaseDialog(R.layout.dialog_coin_market), CoinMarketContract.View {

    lateinit var dismissDialog: (String) -> Unit

    private val presenter: CoinMarketContract.Presenter by lazy {
        CoinMarketPresenter(
            Injection.provideCoinRepository(),
            this
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerview_coin_market.adapter = CoinMarketAdapter(
            clickCoinMarketListener = {
                dismissDialog.invoke(it)
                dismiss()
            }


        )
        presenter.populateCoinData()
    }

    override fun showCoinMarket(coinMarkets: List<String>) {
        (recyclerview_coin_market.adapter as CoinMarketAdapter).refreshData(coinMarkets)
    }

    override fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

}