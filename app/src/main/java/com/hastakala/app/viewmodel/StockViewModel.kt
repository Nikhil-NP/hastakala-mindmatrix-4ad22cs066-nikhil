package com.hastakala.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hastakala.app.data.entity.Product
import com.hastakala.app.utils.RepositoryProvider
import kotlinx.coroutines.launch

class StockViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RepositoryProvider.getRepository(application)
    val products: LiveData<List<Product>> = repository.products

    fun addProduct(name: String, color: String, stock: Int, threshold: Int) {
        viewModelScope.launch {
            repository.addProduct(name, color, stock, threshold)
        }
    }

    fun updateProduct(product: Product, name: String, color: String, stock: Int, threshold: Int) {
        viewModelScope.launch {
            repository.updateProduct(
                product.copy(
                    name = name.trim(),
                    color = color.trim(),
                    currentStock = stock,
                    lowStockThreshold = threshold
                )
            )
        }
    }
}
