package com.hastakala.app.ui.stock

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.hastakala.app.R
import com.hastakala.app.adapter.StockAdapter
import com.hastakala.app.data.entity.Product
import com.hastakala.app.viewmodel.StockViewModel

class StockFragment : Fragment(R.layout.fragment_stock) {
    private val viewModel: StockViewModel by viewModels()
    private lateinit var adapter: StockAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = StockAdapter { product -> showEditStockDialog(product) }
        view.findViewById<RecyclerView>(R.id.stockRecyclerView).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@StockFragment.adapter
        }
        viewModel.products.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        view.findViewById<MaterialButton>(R.id.addProductButton).setOnClickListener {
            showAddProductDialog()
        }
    }

    private fun showAddProductDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_product, null)
        AlertDialog.Builder(requireContext())
            .setTitle("Add Product")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val name = dialogView.findViewById<EditText>(R.id.dialogNameInput).text.toString()
                val color = dialogView.findViewById<EditText>(R.id.dialogColorInput).text.toString()
                val stock = dialogView.findViewById<EditText>(R.id.dialogStockInput).text.toString().toIntOrNull()
                val threshold = dialogView.findViewById<EditText>(R.id.dialogThresholdInput).text.toString().toIntOrNull()
                if (name.isBlank() || color.isBlank() || stock == null || threshold == null) {
                    Toast.makeText(requireContext(), "Fill all details", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addProduct(name, color, stock, threshold)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditStockDialog(product: Product) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_product, null)
        dialogView.findViewById<EditText>(R.id.dialogNameInput).setText(product.name)
        dialogView.findViewById<EditText>(R.id.dialogColorInput).setText(product.color)
        dialogView.findViewById<EditText>(R.id.dialogStockInput).setText(product.currentStock.toString())
        dialogView.findViewById<EditText>(R.id.dialogThresholdInput).setText(product.lowStockThreshold.toString())

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Stock")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val name = dialogView.findViewById<EditText>(R.id.dialogNameInput).text.toString()
                val color = dialogView.findViewById<EditText>(R.id.dialogColorInput).text.toString()
                val stock = dialogView.findViewById<EditText>(R.id.dialogStockInput).text.toString().toIntOrNull()
                val threshold = dialogView.findViewById<EditText>(R.id.dialogThresholdInput).text.toString().toIntOrNull()
                if (name.isBlank() || color.isBlank() || stock == null || threshold == null) {
                    Toast.makeText(requireContext(), "Fill all details", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.updateProduct(product, name, color, stock, threshold)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
