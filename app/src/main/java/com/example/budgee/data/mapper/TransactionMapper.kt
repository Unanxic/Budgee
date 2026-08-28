package com.example.budgee.data.mapper

import com.example.budgee.data.local.entity.TransactionEntity
import com.example.budgee.domain.model.Transaction

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        reason = reason,
        amount = amount,
        isIncome = isIncome,
        timestampMillis = timestampMillis
    )
}

fun Transaction.toEntity(archivedMonthId: Long? = null): TransactionEntity {
    return TransactionEntity(
        id = id,
        reason = reason,
        amount = amount,
        isIncome = isIncome,
        timestampMillis = timestampMillis,
        archivedMonthId = archivedMonthId
    )
}