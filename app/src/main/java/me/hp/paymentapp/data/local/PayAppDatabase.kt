package me.hp.paymentapp.data.local


import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hp.paymentapp.R
import me.hp.paymentapp.common.EMAIL_KEY
import me.hp.paymentapp.data.converters.Converters
import me.hp.paymentapp.data.entity.CurrencyEntity
import me.hp.paymentapp.data.entity.TransactionEntity
import me.hp.paymentapp.data.entity.UserEntity
import me.hp.paymentapp.data.entity.WalletEntity
import me.hp.paymentapp.data.local.PayAppDatabase.Companion.DATABASE_VERSION
import me.hp.paymentapp.data.local.dao.CurrencyDao
import me.hp.paymentapp.data.local.dao.TransactionDao
import me.hp.paymentapp.data.local.dao.UserDao
import me.hp.paymentapp.data.local.dao.WalletDao
import me.hp.paymentapp.data.uimodel.UserWallet
import me.hp.paymentapp.di.qualifier.AppContext
import kotlin.random.Random

@Database(
    entities = [
        UserEntity::class,
        WalletEntity::class,
        TransactionEntity::class,
        CurrencyEntity::class
    ], version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PayAppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "payment_app.db"

        @Volatile
        private var INSTANCE: PayAppDatabase? = null

        fun getInstance(@AppContext context: Context): PayAppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                PayAppDatabase::class.java,
                DATABASE_NAME
            )
                .addCallback(object : Callback() {
                    // Pre-populate the database after onCreate has been called. If you want to prepopulate at opening time then override onOpen function instead of onCreate.
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Do database operations through coroutine or any background thread
                        val handler = CoroutineExceptionHandler { _, exception ->
                            println("Caught during database creation --> $exception")
                        }

                        CoroutineScope(Dispatchers.IO).launch(handler) {
                            prePopulateAppDatabase(
                                context,
                                getInstance(context).currencyDao(),
                                getInstance(context).userDao(),
                                getInstance(context).walletDao()
                            )
                        }
                    }
                })
                .build()

        suspend fun prePopulateAppDatabase(context: Context, currencyDao: CurrencyDao, userDao: UserDao, walletDao: WalletDao) {

            val currenciesUnit = context.resources.getStringArray(R.array.currency_unit_array)
            val currenciesSymbol = context.resources.getStringArray(R.array.currency_symbol_array)
            val exchangeRates = context.resources.getStringArray(R.array.exchange_rate_array).map { it.toDouble() }

            for (i in 1..3) {
                val user = UserEntity(
                    "user$i@gmail.com", "Passion$i",
                    "Jimmy$i", "Carter$i",
                    "12/10/200$i"
                )
                val userId = userDao.insertUser(user)
                if (userId > 0) {
                    val currencyIndex = Random.nextInt(0, currenciesUnit.size)
                    val newWallet = WalletEntity(currenciesUnit[currencyIndex], userId)
                    val walletId = walletDao.insertWallet(newWallet)
                }
            }

            for (i in 0..currenciesUnit.size) {
                val currency = CurrencyEntity(currenciesUnit[i], currenciesSymbol[i], exchangeRates[i])
                currencyDao.insertCurrency(currency)
            }
        }
    }

    abstract fun userDao(): UserDao

    abstract fun walletDao(): WalletDao

    abstract fun transactionDao(): TransactionDao

    abstract fun currencyDao(): CurrencyDao
}