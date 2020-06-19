package me.hp.paymentapp.data.local.dao

import androidx.room.*
import me.hp.paymentapp.data.entity.WalletEntity
import me.hp.paymentapp.data.uimodel.UserWallet

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(walletEntity: WalletEntity): Long

    @Query("SELECT wallets.id, balance, user_id, wallets.currency_unit, currency_symbol FROM wallets INNER JOIN currencies ON wallets.currency_unit = currencies.currency_unit WHERE user_id = :userId")
    suspend fun getWalletByUserId(userId: Long): UserWallet

    @Update
    suspend fun updateWallet(walletEntity: WalletEntity)
}