package com.example.budgee.data.mapper

import com.example.budgee.data.local.entity.ArchivedMonthEntity
import com.example.budgee.domain.model.ArchivedMonth
import com.example.budgee.domain.model.Transaction

fun ArchivedMonthEntity.toDomain(transactions: List<Transaction>): ArchivedMonth {
    return ArchivedMonth(
        id = id,
        monthLabel = monthLabel,
        startingBudget = startingBudget,
        closingBalance = closingBalance,
        transactions = transactions
    )
}

fun ArchivedMonth.toEntity(): ArchivedMonthEntity {
    return ArchivedMonthEntity(
        id = id,
        monthLabel = monthLabel,
        startingBudget = startingBudget,
        closingBalance = closingBalance
    )
}