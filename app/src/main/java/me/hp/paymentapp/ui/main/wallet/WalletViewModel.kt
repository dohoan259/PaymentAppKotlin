package me.hp.paymentapp.ui.main.wallet


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hp.paymentapp.data.entity.TransactionEntity
import me.hp.paymentapp.data.repository.transaction.TransactionRepo
import me.hp.paymentapp.data.repository.user.UserRepo
import me.hp.paymentapp.data.uimodel.WalletUI
import javax.inject.Inject

class WalletViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val transactionRepo: TransactionRepo
): ViewModel() {

    companion object {
        const val TRANSACTION_ITEM_LIMIT = 10
    }

    private var _balance = MutableLiveData<WalletUI>(null)
    val balance: LiveData<WalletUI> = _balance

    private var _transactionList = MutableLiveData<List<TransactionEntity>>(arrayListOf())
    val transactionList = _transactionList

    fun loadData() {
        loadWallet()
        loadTransactions()
    }

    private fun loadWallet() {
        viewModelScope.launch(Dispatchers.IO) {
            val email = userRepo.getCurrentUserEmail()
            if (email != null) {
                val user = userRepo.getUserByEmail(email)
                user?.wallet?.let {
                    _balance.postValue(WalletUI(it.getBalance(), it.currencySymbol))
                }
            }
        }
    }

    fun loadTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepo.getCurrentUser()
            user?.let {
                val trans = transactionRepo.getTransactionWithPage(it.id, TRANSACTION_ITEM_LIMIT, 0)
                _transactionList.postValue(trans)
            }

        }
    }
}