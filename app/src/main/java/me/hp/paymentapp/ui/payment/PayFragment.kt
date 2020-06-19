package me.hp.paymentapp.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import me.hp.paymentapp.R
import me.hp.paymentapp.data.uimodel.OrderItemUI
import me.hp.paymentapp.ui.base.BaseFragment
import java.util.*
import javax.inject.Inject

class PayFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PayViewModel

    private lateinit var orderItem: OrderItemUI
    private lateinit var businessName: TextView
    private lateinit var amount: TextView
    private lateinit var balance: TextView
    private lateinit var payBtn: Button
    private lateinit var progressLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        orderItem = PayFragmentArgs.fromBundle(requireArguments()).orderItem

        viewModel = ViewModelProvider(this, viewModelFactory).get(PayViewModel::class.java)

        viewModel.balance.observe(this, androidx.lifecycle.Observer { wallet ->
                balance.text = String.format(
                    Locale.getDefault(),
                    getString(R.string.currency_value),
                    wallet.currencySymbol,
                    wallet.balance
                )
        })
        viewModel.payResult.observe(this, androidx.lifecycle.Observer { transCode ->
            if (transCode != null) {
                if (transCode.isNotEmpty()) {
                    val action = PayFragmentDirections.actionPayFragmentToPaySuccessFragment(transCode)
                    findNavController().navigate(action)
                } else{
                    Toast.makeText(requireContext(), getString(R.string.failed_pay), Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.showLoading.observe(this, androidx.lifecycle.Observer { isShow ->
            if (isShow) {
                progressLayout.visibility = View.VISIBLE
            } else {
                progressLayout.visibility = View.GONE
            }
        })

        viewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pay, container, false)

        val toolBar = root.findViewById<Toolbar>(R.id.toolbar)
        toolBar.setNavigationIcon(R.drawable.ic_back)
        toolBar.title = ""
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolBar)
        }
        toolBar.setNavigationOnClickListener {
            requireActivity().finish()
        }

        businessName = root.findViewById(R.id.business_name_tv)
        businessName.text = orderItem.businessName

        amount = root.findViewById(R.id.amount_tv)
        balance = root.findViewById(R.id.balance_value_tv)
        payBtn = root.findViewById(R.id.pay_btn)
        progressLayout = root.findViewById(R.id.progress_bar_container)
        setupView()

        return root
    }

    private fun setupView() {
        amount.text = String.format(Locale.getDefault(), getString(R.string.currency_value), orderItem.currencyUnit, orderItem.amount)
        payBtn.text = String.format(Locale.getDefault(), getString(R.string.pay_money), orderItem.currencyUnit, orderItem.amount)

        payBtn.setOnClickListener {
            viewModel.onPayClicked(orderItem)
        }
    }
}
