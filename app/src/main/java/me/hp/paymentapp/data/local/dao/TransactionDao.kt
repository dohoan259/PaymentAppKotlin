package me.hp.paymentapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import me.hp.paymentapp.data.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transactionEntity: TransactionEntity): Long

    @Query("SELECT * FROM transactions")
    fun loadTransactions(): LiveData<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE user_id = :uId ORDER BY create_at DESC LIMIT :limit OFFSET :offset")
    suspend fun loadTransactionWithPage(uId: Long, limit: Int, offset: Int): List<TransactionEntity>
}