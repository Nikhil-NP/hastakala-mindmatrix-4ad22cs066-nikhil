package com.hastakala.app.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    fun todayText(): String = dateFormat.format(Date())

    fun formatDate(timestamp: Long): String = dateFormat.format(Date(timestamp))

    fun rangeFor(filter: DateFilter): Pair<Long, Long> {
        val now = Calendar.getInstance()
        val end = now.timeInMillis
        val start = Calendar.getInstance()

        when (filter) {
            DateFilter.TODAY -> {
                start.set(Calendar.HOUR_OF_DAY, 0)
                start.set(Calendar.MINUTE, 0)
                start.set(Calendar.SECOND, 0)
                start.set(Calendar.MILLISECOND, 0)
            }
            DateFilter.WEEK -> {
                start.firstDayOfWeek = Calendar.MONDAY
                start.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                start.set(Calendar.HOUR_OF_DAY, 0)
                start.set(Calendar.MINUTE, 0)
                start.set(Calendar.SECOND, 0)
                start.set(Calendar.MILLISECOND, 0)
            }
            DateFilter.MONTH -> {
                start.set(Calendar.DAY_OF_MONTH, 1)
                start.set(Calendar.HOUR_OF_DAY, 0)
                start.set(Calendar.MINUTE, 0)
                start.set(Calendar.SECOND, 0)
                start.set(Calendar.MILLISECOND, 0)
            }
            DateFilter.ALL -> {
                start.timeInMillis = 0L
            }
        }
        return start.timeInMillis to end
    }
}

enum class DateFilter(val label: String) {
    TODAY("Today"),
    WEEK("This Week"),
    MONTH("This Month"),
    ALL("All Time")
}
