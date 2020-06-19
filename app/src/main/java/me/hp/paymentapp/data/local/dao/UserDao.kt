package me.hp.paymentapp.data.local.dao

import androidx.room.*
import me.hp.paymentapp.data.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun getUserByCredentials(email: String, password: String): List<UserEntity>

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): List<UserEntity>
}