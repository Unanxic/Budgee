package com.example.budgee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "archived_months")
data class ArchivedMonthEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val monthLabel: String,
    val startingBudget: Double,
    val closingBalance: Double
)