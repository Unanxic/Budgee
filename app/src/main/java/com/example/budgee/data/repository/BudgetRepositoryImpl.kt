package com.example.budgee.data.repository

import com.example.budgee.data.local.dao.ArchivedMonthDao
import com.example.budgee.data.local.dao.TransactionDao
import com.example.budgee.data.local.entity.ArchivedMonthEntity
import com.example.budgee.data.mapper.toDomain
import com.example.budgee.data.mapper.toEntity
import com.example.budgee.data.preferences.SettingsDataStore
import com.example.budgee.domain.model.ArchivedMonth
import com.example.budgee.domain.model.MonthlySettings
import com.example.budgee.domain.model.Transaction
import com.example.budgee.domain.repository.BudgetRepository
import com.example.budgee.domain.util.MonthResetCalculator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId

class BudgetRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val archivedMonthDao: ArchivedMonthDao,
    private val settingsDataStore: SettingsDataStore
) : BudgetRepository {

    override fun observeCurrentMonthTransactions(): Flow<List<Transaction>> {
        return transactionDao.observeCurrentMonthTransactions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun observeSettings(): Flow<MonthlySettings> {
        return settingsDataStore.settings
    }

    override fun observeArchivedMonths(): Flow<List<ArchivedMonth>> {
        return archivedMonthDao.observeAll().map { monthEntities ->
            monthEntities.map { monthEntity ->
                val transactions = transactionDao
                    .observeTransactionsForMonth(monthEntity.id)
                    .first()
                    .map { it.toDomain() }
                monthEntity.toDomain(transactions)
            }
        }
    }

    override suspend fun addTransaction(reason: String, amount: Double, isIncome: Boolean) {
        maybeArchiveElapsedMonth()

        val transaction = Transaction(
            reason = reason,
            amount = amount,
            isIncome = isIncome,
            timestampMillis = System.currentTimeMillis()
        )
        transactionDao.insert(transaction.toEntity())
    }

    override suspend fun deleteTransaction(id: Long) {
        transactionDao.deleteById(id)
    }

    override suspend fun updateSettings(monthlyBudget: Double, resetDay: Int) {
        settingsDataStore.updateSettings(monthlyBudget, resetDay)
    }

    /**
     * Checks whether the current reset period has elapsed since the
     * last known transaction, and if so, archives all current-month
     * transactions under a new [ArchivedMonthEntity] before any new
     * transaction is recorded. Called at the top of [addTransaction]
     * so the check happens lazily, without needing a background job.
     */
    private suspend fun maybeArchiveElapsedMonth() {
        val settings = settingsDataStore.settings.first()
        val currentTransactions = transactionDao.observeCurrentMonthTransactions().first()

        if (currentTransactions.isEmpty()) return

        val today = LocalDate.now()
        val periodStart = MonthResetCalculator.currentPeriodStart(settings.resetDay, today)
        val periodStartMillis = periodStart.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val oldestTransactionMillis = currentTransactions.minOf { it.timestampMillis }
        if (oldestTransactionMillis >= periodStartMillis) return

        val closingBalance = settings.monthlyBudget -
                currentTransactions.filter { !it.isIncome }.sumOf { it.amount } +
                currentTransactions.filter { it.isIncome }.sumOf { it.amount }

        val previousPeriodStart = MonthResetCalculator.currentPeriodStart(
            settings.resetDay,
            periodStart.minusDays(1)
        )
        val monthLabel = formatMonthLabel(previousPeriodStart)

        val archivedMonth = ArchivedMonthEntity(
            monthLabel = monthLabel,
            startingBudget = settings.monthlyBudget,
            closingBalance = closingBalance
        )
        val archivedMonthId = archivedMonthDao.insert(archivedMonth)
        transactionDao.archiveCurrentMonthTransactions(archivedMonthId)
    }

    private fun formatMonthLabel(date: LocalDate): String {
        val formatter = java.time.format.DateTimeFormatter
            .ofPattern("LLLL yyyy", java.util.Locale.forLanguageTag("el"))
        return date.format(formatter).replaceFirstChar { it.uppercase() }
    }
}