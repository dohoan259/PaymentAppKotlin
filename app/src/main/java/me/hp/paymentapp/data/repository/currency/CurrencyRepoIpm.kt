package me.hp.paymentapp.data.repository.currency

import me.hp.paymentapp.data.entity.CurrencyEntity
import me.hp.paymentapp.data.local.PayAppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepoIpm @Inject constructor(private val payAppDatabase: PayAppDatabase) : CurrencyRepo {

    override suspend fun getCurrencyByUnit(unit: String): CurrencyEntity? {
        return payAppDatabase.currencyDao().getCurrencyByUnit(unit)
    }
}