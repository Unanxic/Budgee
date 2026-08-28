package com.example.budgee.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budgee.data.local.dao.ArchivedMonthDao
import com.example.budgee.data.local.dao.TransactionDao
import com.example.budgee.data.local.entity.ArchivedMonthEntity
import com.example.budgee.data.local.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class, ArchivedMonthEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun archivedMonthDao(): ArchivedMonthDao
}