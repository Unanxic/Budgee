package com.example.budgee.domain.util

import com.example.budgee.domain.util.MonthResetCalculator.currentPeriodStart
import java.time.LocalDate
import java.time.YearMonth

/**
 * Pure logic for deciding when a monthly budget period has ended,
 * given the user's chosen reset day.
 */
object MonthResetCalculator {

    /**
     * The current period's start date, given [resetDay] and [today].
     * If today is on or after the reset day this month, the period
     * started this month; otherwise it started on the reset day of the
     * previous month. Clamps to the last valid day of a shorter month
     * (e.g. resetDay=31 in February).
     */
    fun currentPeriodStart(resetDay: Int, today: LocalDate = LocalDate.now()): LocalDate {
        val thisMonthResetDate = clampToMonth(YearMonth.from(today), resetDay)
        return if (today.isBefore(thisMonthResetDate)) {
            val previousMonth = YearMonth.from(today).minusMonths(1)
            clampToMonth(previousMonth, resetDay)
        } else {
            thisMonthResetDate
        }
    }

    /**
     * The date the current period will end (i.e. the next reset date
     * after [currentPeriodStart]).
     */
    fun currentPeriodEnd(resetDay: Int, today: LocalDate = LocalDate.now()): LocalDate {
        val start = currentPeriodStart(resetDay, today)
        val nextMonth = YearMonth.from(start).plusMonths(1)
        return clampToMonth(nextMonth, resetDay)
    }

    private fun clampToMonth(yearMonth: YearMonth, day: Int): LocalDate {
        val lastDayOfMonth = yearMonth.lengthOfMonth()
        return yearMonth.atDay(day.coerceIn(1, lastDayOfMonth))
    }
}