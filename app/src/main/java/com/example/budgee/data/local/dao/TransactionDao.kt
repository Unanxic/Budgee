package com.example.budgee.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.budgee.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions WHERE archivedMonthId IS NULL ORDER BY timestampMillis DESC")
    fun observeCurrentMonthTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE archivedMonthId = :archivedMonthId ORDER BY timestampMillis DESC")
    fun observeTransactionsForMonth(archivedMonthId: Long): Flow<List<TransactionEntity>>

    @Insert
    suspend fun insert(transaction: TransactionEntity): Long

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE transactions SET archivedMonthId = :archivedMonthId WHERE archivedMonthId IS NULL")
    suspend fun archiveCurrentMonthTransactions(archivedMonthId: Long)

    @Delete
    suspend fun delete(transaction: TransactionEntity)
}