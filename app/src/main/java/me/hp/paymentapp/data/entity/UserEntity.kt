package me.hp.paymentapp.data.entity

import androidx.room.*
import me.hp.paymentapp.data.uimodel.UserWallet

@Entity(tableName = "users", indices = [Index(value = ["email"], unique = true)])
class UserEntity (

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = "first_name")
    var firstName: String,

    @ColumnInfo(name = "last_name")
    var lastName: String,

    @ColumnInfo(name = "birthday")
    var birthday: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
) {

    @Ignore
    lateinit var wallet: UserWallet
}