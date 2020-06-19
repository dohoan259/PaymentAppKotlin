package me.hp.paymentapp.data.uimodel


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class OrderItemUI(
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("business_name")
    val businessName: String,
    val amount: Double,
    @SerializedName("currency_unit")
    val currencyUnit: String
) : Serializable