package me.hp.paymentapp.data.uimodel

import androidx.room.ColumnInfo
import kotlin.math.roundToLong

class UserWallet(

    @ColumnInfo(name = "currency_unit")
    var currencyUnit: String,

    @ColumnInfo(name = "currency_symbol")
    var currencySymbol: String,

    @ColumnInfo(name = "user_id")
    var userId: Long,

    @ColumnInfo(name = "balance")
    private var balance: Double,

    @ColumnInfo(name = "id")
    var id: Long = 0
) {

    fun getBalance(): Double = balance

    fun changeBalance(amount: Double) {
        balance += amount

        var temp = balance
        temp *= 100
        temp = temp.roundToLong().toDouble()
        temp /= 100

        balance = temp
    }
}