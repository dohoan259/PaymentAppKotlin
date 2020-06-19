package me.hp.paymentapp.ui.main.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_wallet.*
import me.hp.paymentapp.R
import me.hp.paymentapp.ui.base.BaseFragment
import javax.inject.Inject
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class WalletFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: WalletViewModel

    private lateinit var transAdapter: TransactionAdapter
    private lateinit var transRv: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WalletViewModel::class.java)

        viewModel.balance.observe(this, Observer { value ->
            if (value != null) {
                balance_tv.text = value.balance.toString()
                currency_unit_tv.text = value.currencySymbol
            }
        })
        viewModel.transactionList.observe(this, Observer {
            transAdapter.loadData(it)
        })

        viewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_wallet, container, false)

        transRv = root.findViewById(R.id.transaction_rv)
        swipeRefresh = root.findViewById(R.id.swiperefresh)

        setupView()

        return root
    }

    private fun setupView() {
        swipeRefresh.setOnRefreshListener {
            viewModel.loadTransactions()
            swipeRefresh.isRefreshing = false
        }
        val publishedLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        transRv.layoutManager = publishedLayoutManager
        transAdapter = TransactionAdapter()
        transRv.adapter = transAdapter
    }
}