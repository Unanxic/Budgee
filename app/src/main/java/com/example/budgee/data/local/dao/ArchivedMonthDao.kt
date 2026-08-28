package com.example.budgee.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.budgee.data.local.entity.ArchivedMonthEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArchivedMonthDao {

    @Query("SELECT * FROM archived_months ORDER BY id DESC")
    fun observeAll(): Flow<List<ArchivedMonthEntity>>

    @Insert
    suspend fun insert(archivedMonth: ArchivedMonthEntity): Long
}