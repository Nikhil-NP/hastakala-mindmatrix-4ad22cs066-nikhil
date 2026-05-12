package com.hastakala.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hastakala.app.data.model.SaleWithProduct
import com.hastakala.app.utils.DateFilter
import com.hastakala.app.utils.RepositoryProvider
import kotlinx.coroutines.launch

class IncomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RepositoryProvider.getRepository(application)
    val filter = MutableLiveData(DateFilter.WEEK)

    val sales: LiveData<List<SaleWithProduct>> = Transformations.switchMap(filter) {
        repository.getSales(it)
    }
    val income: LiveData<Double> = Transformations.switchMap(filter) {
        repository.getIncome(it)
    }

    fun deleteSale(saleId: Int) {
        viewModelScope.launch {
            repository.deleteSaleAndRestoreStock(saleId)
        }
    }
}
