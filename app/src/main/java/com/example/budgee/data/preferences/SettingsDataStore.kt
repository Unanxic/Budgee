package com.example.budgee.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.budgee.domain.model.MonthlySettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "budgee_settings"
)

private object SettingsKeys {
    val MONTHLY_BUDGET = doublePreferencesKey("monthly_budget")
    val RESET_DAY = intPreferencesKey("reset_day")
}

private const val DEFAULT_MONTHLY_BUDGET = 0.0
private const val DEFAULT_RESET_DAY = 1

class SettingsDataStore(private val context: Context) {

    val settings: Flow<MonthlySettings> = context.settingsDataStore.data.map { preferences ->
        MonthlySettings(
            monthlyBudget = preferences[SettingsKeys.MONTHLY_BUDGET] ?: DEFAULT_MONTHLY_BUDGET,
            resetDay = preferences[SettingsKeys.RESET_DAY] ?: DEFAULT_RESET_DAY
        )
    }

    val hasBeenConfigured: Flow<Boolean> = context.settingsDataStore.data.map { preferences ->
        preferences.contains(SettingsKeys.MONTHLY_BUDGET)
    }

    suspend fun updateSettings(monthlyBudget: Double, resetDay: Int) {
        context.settingsDataStore.edit { preferences ->
            preferences[SettingsKeys.MONTHLY_BUDGET] = monthlyBudget
            preferences[SettingsKeys.RESET_DAY] = resetDay
        }
    }
}