package me.hp.paymentapp.data.local.dao

import androidx.room.*
import me.hp.paymentapp.data.entity.CurrencyEntity

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currencyEntity: CurrencyEntity): Long

    @Query("SELECT * FROM currencies")
    suspend fun loadCurrencies(): List<CurrencyEntity>

    @Query("SELECT * FROM currencies WHERE currency_unit = :currencyUnit")
    suspend fun getCurrencyByUnit(currencyUnit: String): CurrencyEntity?

    @Update
    suspend fun updateCurrency(currencyEntity: CurrencyEntity)
}