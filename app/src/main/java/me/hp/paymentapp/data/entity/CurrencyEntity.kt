package me.hp.paymentapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "currencies", indices = [Index(value = ["currency_unit"], unique = true)])
class CurrencyEntity (

    @ColumnInfo(name = "currency_unit")
    var currencyUnit: String,

    @ColumnInfo(name = "currency_symbol")
    var currencySymbol: String,

    @ColumnInfo(name = "exchange_rate")
    var exchangeRate: Double,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)