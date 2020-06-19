package me.hp.paymentapp.data.converters

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun fromDateString(value: String): Long = Date(value).time

    @TypeConverter
    fun toDate(timeStamp: Long) = Date(timeStamp)
}