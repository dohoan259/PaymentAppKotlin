package me.hp.paymentapp.data.repository.wallet


import me.hp.paymentapp.data.entity.WalletEntity
import me.hp.paymentapp.data.local.PayAppDatabase
import me.hp.paymentapp.data.uimodel.UserWallet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepoIpm @Inject constructor(private val payAppDatabase: PayAppDatabase) : WalletRepo{

    override suspend fun createWallet(walletEntity: WalletEntity): Long {
        return payAppDatabase.walletDao().insertWallet(walletEntity)
    }

    override suspend fun updateWallet(walletEntity: WalletEntity) {
        return payAppDatabase.walletDao().updateWallet(walletEntity)
    }

    override suspend fun getWalletByUser(userId: Long): UserWallet? {
        return payAppDatabase.walletDao().getWalletByUserId(userId)
    }
}