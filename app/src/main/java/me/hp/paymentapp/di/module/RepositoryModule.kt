package me.hp.paymentapp.di.module

import dagger.Binds
import dagger.Module
import me.hp.paymentapp.data.repository.currency.CurrencyRepo
import me.hp.paymentapp.data.repository.currency.CurrencyRepoIpm
import me.hp.paymentapp.data.repository.transaction.TransactionRepo
import me.hp.paymentapp.data.repository.transaction.TransactionRepoIpm
import me.hp.paymentapp.data.repository.user.UserRepo
import me.hp.paymentapp.data.repository.user.UserRepoIpm
import me.hp.paymentapp.data.repository.wallet.WalletRepo
import me.hp.paymentapp.data.repository.wallet.WalletRepoIpm

@Module
interface RepositoryModule {

    @Binds
    fun userRepo(userRepoIpm: UserRepoIpm): UserRepo

    @Binds
    fun walletRepo(walletRepoIpm: WalletRepoIpm): WalletRepo

    @Binds
    fun transactionRepo(transactionRepoIpm: TransactionRepoIpm): TransactionRepo

    @Binds
    fun currencyRepo(currencyRepoIpm: CurrencyRepoIpm): CurrencyRepo
}