package com.hastakala.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hastakala.app.R
import com.hastakala.app.data.entity.Product

class StockAdapter(
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {
    private val items = mutableListOf<Product>()

    fun submitList(products: List<Product>) {
        items.clear()
        items.addAll(products)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return StockViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.productIcon)
        private val title: TextView = itemView.findViewById(R.id.productTitle)
        private val subtitle: TextView = itemView.findViewById(R.id.productSubtitle)
        private val status: View = itemView.findViewById(R.id.stockStatus)

        fun bind(product: Product) {
            icon.setImageResource(product.iconRes)
            title.text = "${product.name} (${product.color})"
            subtitle.text = "Stock: ${product.currentStock}  Threshold: ${product.lowStockThreshold}"
            status.setBackgroundResource(
                when {
                    product.currentStock <= 0 -> R.drawable.status_empty
                    product.currentStock <= product.lowStockThreshold -> R.drawable.status_low
                    else -> R.drawable.status_good
                }
            )
            itemView.setOnClickListener { onProductClick(product) }
        }
    }
}
