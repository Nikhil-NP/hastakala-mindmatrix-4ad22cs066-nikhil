package com.hastakala.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hastakala.app.R
import com.hastakala.app.data.model.SaleWithProduct

class IncomeAdapter : RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder>() {
    private val items = mutableListOf<SaleWithProduct>()

    fun submitList(sales: List<SaleWithProduct>) {
        items.clear()
        items.addAll(sales)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): SaleWithProduct = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_income, parent, false)
        return IncomeViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class IncomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.saleTitle)
        private val amount: TextView = itemView.findViewById(R.id.saleAmount)
        private val details: TextView = itemView.findViewById(R.id.saleDetails)

        fun bind(sale: SaleWithProduct) {
            title.text = "${sale.productName} (${sale.color})"
            amount.text = "Rs ${sale.totalAmount.toInt()}"
            details.text = "${sale.quantity} x Rs ${sale.pricePerUnit.toInt()}  ${sale.saleDate}"
        }
    }
}
