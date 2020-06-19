package me.hp.paymentapp.data.repository.user


import me.hp.paymentapp.data.entity.UserEntity

interface UserRepo {

    suspend fun createUser(userEntity: UserEntity): Long

    suspend fun getUserByCredentials(email: String, password: String): UserEntity?

    suspend fun getUserByEmail(email: String): UserEntity?

    fun getCurrentUserEmail(): String?

    suspend fun getCurrentUser(): UserEntity?

    fun logout()

    fun saveCredentials(email: String)
}