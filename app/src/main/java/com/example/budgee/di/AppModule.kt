package com.example.budgee.di

import android.content.Context
import androidx.room.Room
import com.example.budgee.data.local.AppDatabase
import com.example.budgee.data.local.dao.ArchivedMonthDao
import com.example.budgee.data.local.dao.TransactionDao
import com.example.budgee.data.preferences.SettingsDataStore
import com.example.budgee.data.repository.BudgetRepositoryImpl
import com.example.budgee.domain.repository.BudgetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "budgee.db"
        ).build()
    }

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    fun provideArchivedMonthDao(database: AppDatabase): ArchivedMonthDao {
        return database.archivedMonthDao()
    }

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context)
    }

    @Provides
    @Singleton
    fun provideBudgetRepository(
        transactionDao: TransactionDao,
        archivedMonthDao: ArchivedMonthDao,
        settingsDataStore: SettingsDataStore
    ): BudgetRepository {
        return BudgetRepositoryImpl(transactionDao, archivedMonthDao, settingsDataStore)
    }
}