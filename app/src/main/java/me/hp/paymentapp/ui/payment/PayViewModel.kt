package me.hp.paymentapp.ui.payment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hp.paymentapp.data.repository.currency.CurrencyRepo
import me.hp.paymentapp.data.repository.user.UserRepo
import me.hp.paymentapp.data.uimodel.OrderItemUI
import me.hp.paymentapp.data.uimodel.WalletUI
import me.hp.paymentapp.domain.PayItem
import javax.inject.Inject

class PayViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val payItem: PayItem
): ViewModel() {

    private var _balance = MutableLiveData<WalletUI>()
    val balance: LiveData<WalletUI> = _balance

    private val _payResult = MutableLiveData<String?>(null)
    var payResult: LiveData<String?> = _payResult

    private val _showLoading = MutableLiveData<Boolean>(false)
    var showLoading = _showLoading

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.getCurrentUser()?.let {
                _balance.postValue(WalletUI(it.wallet.getBalance(), it.wallet.currencySymbol))
            }
        }
    }

    fun onPayClicked(orderItem: OrderItemUI) {

        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            val transCode = payItem.pay(orderItem)
            _showLoading.postValue(false)
            _payResult.postValue(transCode)

        }
    }
}
