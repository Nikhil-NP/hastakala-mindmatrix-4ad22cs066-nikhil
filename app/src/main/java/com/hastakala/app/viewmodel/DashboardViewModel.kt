package com.hastakala.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.hastakala.app.data.model.DailyRevenue
import com.hastakala.app.data.model.SalesSummary
import com.hastakala.app.utils.DateFilter
import com.hastakala.app.utils.RepositoryProvider

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RepositoryProvider.getRepository(application)
    val filter = MutableLiveData(DateFilter.TODAY)

    val summary: LiveData<List<SalesSummary>> = Transformations.switchMap(filter) {
        repository.getSalesSummary(it)
    }
    val dailyRevenue: LiveData<List<DailyRevenue>> = Transformations.switchMap(filter) {
        repository.getDailyRevenue(it)
    }
    val income: LiveData<Double> = Transformations.switchMap(filter) {
        repository.getIncome(it)
    }
    val itemsSold: LiveData<Int> = Transformations.switchMap(filter) {
        repository.getItemsSold(it)
    }
}
