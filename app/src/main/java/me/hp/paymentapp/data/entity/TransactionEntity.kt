package me.hp.paymentapp.data.entity

import androidx.room.*
import me.hp.paymentapp.data.uimodel.OrderItemUI
import java.util.*

@Entity(
    tableName = "transactions",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["user_id"])]
)
class TransactionEntity (

    @ColumnInfo(name = "product_name")
    var productName: String,

    @ColumnInfo(name = "business_name")
    var businessName: String,

    @ColumnInfo(name = "amount")
    var amount: Double,

    @ColumnInfo(name = "currency_unit")
    var currencyUnit: String,

    @ColumnInfo(name = "transaction_code")
    var transactionCode: String,

    @ColumnInfo(name = "user_id")
    var userId: Long,

    @ColumnInfo(name = "type")
    var type: Int = 1,

    @ColumnInfo(name = "create_at")
    var createAt: Long = System.currentTimeMillis(),

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
) {



    constructor(orderItemUI: OrderItemUI, userId: Long) : this (
        orderItemUI.productName,
        orderItemUI.businessName,
        orderItemUI.amount,
        orderItemUI.currencyUnit,
        UUID.randomUUID().toString(),
        userId = userId
    )
}