package me.hp.paymentapp.ui.main.wallet


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import me.hp.paymentapp.R
import me.hp.paymentapp.data.entity.TransactionEntity
import java.util.*
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.DiffUtil



class TransactionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var tranList = ArrayList<TransactionEntity>()


    fun loadData(newData: List<TransactionEntity>) {

        val trackDiffCallback = TransactionDiffCallback(this.tranList, newData)
        val diffResult = DiffUtil.calculateDiff(trackDiffCallback)
        this.tranList.clear()
        this.tranList.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.transaction_item_layout, parent, false)
            return TransactionHolder(view)
    }

    override fun getItemCount(): Int {
        return tranList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TransactionHolder).bind(tranList[position])
    }

    internal inner class TransactionHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private var productName: TextView = itemView.findViewById(R.id.product_tv)
        private var businessName: TextView = itemView.findViewById(R.id.business_tv)
        private var itemCost: TextView = itemView.findViewById(R.id.item_cost_tv)

        fun bind(transactionEntity: TransactionEntity) {
            productName.text = transactionEntity.productName
            businessName.text = String.format(Locale.getDefault(),
                itemView.context.getString(R.string.pair_to_business), transactionEntity.businessName)
            itemCost.text = "- ${transactionEntity.currencyUnit} ${transactionEntity.amount}"
        }
    }
}