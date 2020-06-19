package me.hp.paymentapp.data.repository.transaction

import me.hp.paymentapp.data.entity.TransactionEntity
import me.hp.paymentapp.data.local.PayAppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepoIpm @Inject constructor(
    private var payAppDatabase: PayAppDatabase
) : TransactionRepo{

    override suspend fun insertTransaction(transactionEntity: TransactionEntity): Long {
        return payAppDatabase.transactionDao().insertTransaction(transactionEntity)
    }

    override suspend fun getTransactionWithPage(uId: Long, limit: Int, offset: Int): List<TransactionEntity> {
        return payAppDatabase.transactionDao().loadTransactionWithPage(uId, limit, offset)
    }
}