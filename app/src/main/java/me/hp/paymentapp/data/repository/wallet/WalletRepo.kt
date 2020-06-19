package me.hp.paymentapp.data.repository.wallet


import me.hp.paymentapp.data.entity.WalletEntity
import me.hp.paymentapp.data.uimodel.UserWallet

interface WalletRepo {

    suspend fun createWallet(walletEntity: WalletEntity): Long

    suspend fun updateWallet(walletEntity: WalletEntity)

    suspend fun getWalletByUser(userId: Long): UserWallet?
}