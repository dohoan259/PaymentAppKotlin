package me.hp.paymentapp.data.repository.transaction

import me.hp.paymentapp.data.entity.TransactionEntity

interface TransactionRepo {

    suspend fun insertTransaction(transactionEntity: TransactionEntity): Long

    suspend fun getTransactionWithPage(uId: Long, limit: Int, offset: Int): List<TransactionEntity>
}