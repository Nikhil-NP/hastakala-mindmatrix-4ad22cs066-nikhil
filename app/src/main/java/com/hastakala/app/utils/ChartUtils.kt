package com.hastakala.app.utils

import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.hastakala.app.data.model.DailyRevenue
import com.hastakala.app.data.model.SalesSummary

object ChartUtils {
    private val chartColors = listOf(
        Color.rgb(91, 157, 255),
        Color.rgb(242, 95, 92),
        Color.rgb(74, 190, 118),
        Color.rgb(246, 195, 74),
        Color.rgb(177, 131, 255)
    )

    fun setupPieChart(chart: PieChart) {
        chart.description.isEnabled = false
        chart.setUsePercentValues(false)
        chart.setHoleColor(Color.TRANSPARENT)
        chart.setEntryLabelColor(Color.WHITE)
        chart.setNoDataText("No sales yet")
        chart.setNoDataTextColor(Color.WHITE)
        chart.legend.textColor = Color.WHITE
        chart.legend.orientation = Legend.LegendOrientation.VERTICAL
    }

    fun renderPieChart(chart: PieChart, items: List<SalesSummary>, totalIncome: Double) {
        if (items.isEmpty()) {
            chart.clear()
            return
        }
        val entries = items.map { PieEntry(it.quantitySold.toFloat(), it.label) }
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = chartColors
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 13f
        chart.data = PieData(dataSet)
        chart.centerText = "Rs ${totalIncome.toInt()}\nTotal"
        chart.setCenterTextColor(Color.WHITE)
        chart.setCenterTextSize(16f)
        chart.animateY(700)
        chart.invalidate()
    }

    fun setupBarChart(chart: BarChart) {
        chart.description.isEnabled = false
        chart.setNoDataText("No revenue yet")
        chart.setNoDataTextColor(Color.WHITE)
        chart.axisLeft.textColor = Color.WHITE
        chart.axisRight.isEnabled = false
        chart.xAxis.textColor = Color.WHITE
        chart.legend.isEnabled = false
    }

    fun renderBarChart(chart: BarChart, items: List<DailyRevenue>) {
        if (items.isEmpty()) {
            chart.clear()
            return
        }
        val entries = items.mapIndexed { index, item -> BarEntry(index.toFloat(), item.revenue.toFloat()) }
        val labels = items.map { it.saleDate.take(6) }
        val dataSet = BarDataSet(entries, "Revenue")
        dataSet.color = Color.rgb(91, 157, 255)
        dataSet.valueTextColor = Color.WHITE
        chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return labels.getOrNull(value.toInt()).orEmpty()
            }
        }
        chart.data = BarData(dataSet)
        chart.animateY(700)
        chart.invalidate()
    }
}
