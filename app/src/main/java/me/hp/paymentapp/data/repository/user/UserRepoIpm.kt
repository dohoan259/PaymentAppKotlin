package me.hp.paymentapp.data.repository.user

import android.content.SharedPreferences
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.room.Transaction
import me.hp.paymentapp.common.EMAIL_KEY
import me.hp.paymentapp.data.entity.UserEntity
import me.hp.paymentapp.data.entity.WalletEntity
import me.hp.paymentapp.data.local.PayAppDatabase
import me.hp.paymentapp.data.uimodel.UserWallet
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class UserRepoIpm @Inject constructor(private val payAppDatabase: PayAppDatabase, private val pref: SharedPreferences) : UserRepo {

    private var _user: UserEntity? = null

    @Transaction
    override suspend fun createUser(userEntity: UserEntity): Long {
        var userId: Long = -1
        try {
            userId = payAppDatabase.userDao().insertUser(userEntity)
            if (userId > 0) {
                userEntity.id = userId

                val currencies = payAppDatabase.currencyDao().loadCurrencies()
                val currencyIndex = Random.nextInt(0, currencies.size)
                val newWallet = WalletEntity(currencies[currencyIndex].currencyUnit, userId)
                val walletId = payAppDatabase.walletDao().insertWallet(newWallet)
                newWallet.id = walletId
                userEntity.wallet = UserWallet(newWallet.currencyUnit, currencies[currencyIndex].currencySymbol,
                    newWallet.userId, newWallet.getBalance(),
                    walletId)

                // save user info for next login
                pref.edit().putString(EMAIL_KEY, userEntity.email)
                    .apply()

                _user = userEntity
            }
        } catch (ex: SQLiteConstraintException) {
            Log.d("hoan.dv", "ex: $ex")
        }

        return userId
    }

    override suspend fun getUserByCredentials(email: String, password: String): UserEntity? {

        if (_user != null) {
            return _user
        }

        val data = payAppDatabase.userDao().getUserByCredentials(email, password)
        if (data.isNotEmpty()) {
            val user = data[0]
            val wallet = payAppDatabase.walletDao().getWalletByUserId(user.id)
            wallet.let {
                user.wallet = it
            }

            _user = user
        }

        return _user
    }

    override fun getCurrentUserEmail(): String? {
        return pref.getString(EMAIL_KEY, null)
    }

    override suspend fun getCurrentUser(): UserEntity? {
        if (_user != null) {
            return _user
        }
        val email = getCurrentUserEmail()
        if (email != null) {
            return getUserByEmail(email)
        }

        return null
    }

    override suspend fun getUserByEmail(email: String): UserEntity? {
        if (_user != null) {
            return _user
        }

        val data = payAppDatabase.userDao().getUserByEmail(email)
        if (data.isNotEmpty()){
            val user = data[0]
            val wallet = payAppDatabase.walletDao().getWalletByUserId(user.id)
            user.wallet = wallet
            _user = user
        }

        return _user
    }

    override fun logout() {
        pref.edit().remove(EMAIL_KEY)
            .apply()
        _user = null
    }

    override fun saveCredentials(email: String) {
        pref.edit().putString(EMAIL_KEY, email)
            .apply()
    }
}