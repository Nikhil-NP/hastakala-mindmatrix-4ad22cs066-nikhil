package com.hastakala.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hastakala.app.data.entity.Product
import com.hastakala.app.utils.RepositoryProvider
import kotlinx.coroutines.launch

class BillingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RepositoryProvider.getRepository(application)
    val products: LiveData<List<Product>> = repository.products

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun saveSale(productId: Int, quantityText: String, priceText: String) {
        val quantity = quantityText.toIntOrNull()
        val price = priceText.toDoubleOrNull()
        if (quantity == null || price == null) {
            _message.value = "Enter quantity and price"
            return
        }
        viewModelScope.launch {
            val result = repository.saveSale(productId, quantity, price)
            _message.value = result.fold(
                onSuccess = { "Sale saved" },
                onFailure = { it.message ?: "Could not save sale" }
            )
        }
    }
}
