package com.example.budgee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val reason: String,
    val amount: Double,
    val isIncome: Boolean,
    val timestampMillis: Long,
    val archivedMonthId: Long? = null
)