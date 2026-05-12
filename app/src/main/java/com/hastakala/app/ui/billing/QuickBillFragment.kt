package com.hastakala.app.ui.billing

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.hastakala.app.R
import com.hastakala.app.data.entity.Product
import com.hastakala.app.viewmodel.BillingViewModel

class QuickBillFragment : Fragment(R.layout.fragment_quick_bill) {
    private val viewModel: BillingViewModel by viewModels()
    private var selectedProduct: Product? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val productInput: AutoCompleteTextView = view.findViewById(R.id.productInput)
        val quantityInput: EditText = view.findViewById(R.id.quantityInput)
        val priceInput: EditText = view.findViewById(R.id.priceInput)
        val totalText: TextView = view.findViewById(R.id.totalText)

        fun updateTotal() {
            val quantity = quantityInput.text.toString().toIntOrNull() ?: 0
            val price = priceInput.text.toString().toDoubleOrNull() ?: 0.0
            totalText.text = "Total: Rs ${(quantity * price).toInt()}"
        }

        quantityInput.doAfterTextChanged { updateTotal() }
        priceInput.doAfterTextChanged { updateTotal() }

        viewModel.products.observe(viewLifecycleOwner) { products ->
            val labels = products.map { "${it.name} (${it.color}) - Stock ${it.currentStock}" }
            productInput.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, labels))
            productInput.setOnClickListener { productInput.showDropDown() }
            productInput.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) productInput.showDropDown()
            }
            productInput.setOnItemClickListener { _, _, position, _ ->
                selectedProduct = products[position]
            }
        }

        view.findViewById<MaterialButton>(R.id.saveSaleButton).setOnClickListener {
            val product = selectedProduct
            if (product == null) {
                Toast.makeText(requireContext(), "Select product", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.saveSale(product.id, quantityInput.text.toString(), priceInput.text.toString())
            }
        }

        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            if (it == "Sale saved") {
                quantityInput.text.clear()
                priceInput.text.clear()
                productInput.text.clear()
                selectedProduct = null
                updateTotal()
            }
        }
    }
}
