package com.hastakala.app.ui.dashboard

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hastakala.app.MainActivity
import com.hastakala.app.R
import com.hastakala.app.utils.ChartUtils
import com.hastakala.app.utils.DateFilter
import com.hastakala.app.viewmodel.DashboardViewModel

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private val viewModel: DashboardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val revenueText: TextView = view.findViewById(R.id.revenueText)
        val itemsText: TextView = view.findViewById(R.id.itemsText)
        val titleText: TextView = view.findViewById(R.id.overviewTitle)
        val pieChart: PieChart = view.findViewById(R.id.pieChart)
        val barChart: BarChart = view.findViewById(R.id.barChart)

        ChartUtils.setupPieChart(pieChart)
        ChartUtils.setupBarChart(barChart)

        setupFilterButton(view, R.id.todayButton, DateFilter.TODAY)
        setupFilterButton(view, R.id.weekButton, DateFilter.WEEK)
        setupFilterButton(view, R.id.monthButton, DateFilter.MONTH)
        setupFilterButton(view, R.id.allButton, DateFilter.ALL)

        var currentIncome = 0.0
        var currentSummary = emptyList<com.hastakala.app.data.model.SalesSummary>()

        viewModel.filter.observe(viewLifecycleOwner) {
            titleText.text = "Sales Overview - ${it.label}"
        }
        viewModel.income.observe(viewLifecycleOwner) {
            currentIncome = it
            revenueText.text = "Rs ${it.toInt()}"
            ChartUtils.renderPieChart(pieChart, currentSummary, currentIncome)
        }
        viewModel.itemsSold.observe(viewLifecycleOwner) {
            itemsText.text = "$it items sold"
        }
        viewModel.summary.observe(viewLifecycleOwner) {
            currentSummary = it
            ChartUtils.renderPieChart(pieChart, it, currentIncome)
        }
        viewModel.dailyRevenue.observe(viewLifecycleOwner) {
            ChartUtils.renderBarChart(barChart, it)
        }
        view.findViewById<FloatingActionButton>(R.id.addSaleFab).setOnClickListener {
            (requireActivity() as MainActivity).openBilling()
        }
    }

    private fun setupFilterButton(view: View, id: Int, filter: DateFilter) {
        view.findViewById<MaterialButton>(id).setOnClickListener {
            viewModel.filter.value = filter
        }
    }
}
