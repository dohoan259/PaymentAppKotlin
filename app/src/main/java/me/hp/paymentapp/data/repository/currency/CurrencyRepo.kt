package me.hp.paymentapp.data.repository.currency

import me.hp.paymentapp.data.entity.CurrencyEntity

interface CurrencyRepo {

    suspend fun getCurrencyByUnit(unit: String): CurrencyEntity?
}