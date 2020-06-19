package me.hp.paymentapp.ui.main.wallet

import androidx.recyclerview.widget.DiffUtil
import me.hp.paymentapp.data.entity.TransactionEntity

class TransactionDiffCallback(private val oldTrackList: List<TransactionEntity> ,
                              private val newTrackList: List<TransactionEntity> ) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTrackList[oldItemPosition].id == newTrackList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldTrackList.size
    }

    override fun getNewListSize(): Int {
        return newTrackList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTrackList[oldItemPosition].transactionCode == newTrackList[newItemPosition].transactionCode
    }
}