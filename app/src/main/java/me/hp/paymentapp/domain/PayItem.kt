package me.hp.paymentapp.domain

import androidx.room.Transaction
import me.hp.paymentapp.data.entity.TransactionEntity
import me.hp.paymentapp.data.entity.WalletEntity
import me.hp.paymentapp.data.repository.currency.CurrencyRepo
import me.hp.paymentapp.data.repository.transaction.TransactionRepo
import me.hp.paymentapp.data.repository.user.UserRepo
import me.hp.paymentapp.data.repository.wallet.WalletRepo
import me.hp.paymentapp.data.uimodel.OrderItemUI
import javax.inject.Inject

class PayItem @Inject constructor(private val userRepo: UserRepo,
                                  private val currencyRepo: CurrencyRepo,
                                  private val walletRepo: WalletRepo,
                                  private val transactionRepo: TransactionRepo
) {

    @Transaction
    suspend fun pay(orderItem: OrderItemUI): String {
        val user = userRepo.getCurrentUser()

        user?.apply {
            val walletCurrency = currencyRepo.getCurrencyByUnit(wallet.currencyUnit)
            val itemCurrency = currencyRepo.getCurrencyByUnit(orderItem.currencyUnit)

            if (walletCurrency != null && itemCurrency != null) {
                val balance = this.wallet.getBalance()
                val amountFollowBalance = orderItem.amount * itemCurrency.exchangeRate / walletCurrency.exchangeRate



                if (balance >= amountFollowBalance) {
                    wallet.changeBalance((-1) * amountFollowBalance)

                    // update wallet and save transaction
                    walletRepo.updateWallet(WalletEntity(wallet))
                    val transaction = TransactionEntity(
                        orderItem,
                        this.id
                    )
                    transactionRepo.insertTransaction(transaction)
                    return transaction.transactionCode
                }

                return ""
            }
        }

        return ""
    }
}