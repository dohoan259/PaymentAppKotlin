package me.hp.paymentapp.data.entity

import androidx.room.*
import me.hp.paymentapp.data.uimodel.UserWallet
import me.hp.paymentapp.data.uimodel.WalletUI
import kotlin.math.roundToLong

@Entity(
    tableName = "wallets",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["user_id"])]
)
class WalletEntity constructor(

    @ColumnInfo(name = "currency_unit")
    var currencyUnit: String,

    @ColumnInfo(name = "user_id")
    var userId: Long,

    @ColumnInfo(name = "balance")
    private var balance: Double = (Math.random() * 1000.00f).roundToLong().toDouble(),

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
) {

    fun getBalance(): Double = balance

    fun changeBalance(amount: Double) {
        balance += amount
    }

    constructor(userWallet: UserWallet) : this(
        userWallet.currencyUnit,
        userWallet.userId,
        userWallet.getBalance(),
        userWallet.id
    ) {

    }
}