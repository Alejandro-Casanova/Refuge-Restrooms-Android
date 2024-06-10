package com.example.refugerestrooms.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.refugerestrooms.ui.PreferencesUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
){

//    val isThemeSameAsSystem: Flow<Boolean> = dataStore.data
//        .catch {
//            if(it is IOException) {
//                Log.e(TAG, "Error reading preferences.", it)
//                emit(emptyPreferences())
//            } else {
//                throw it
//            }
//        }
//        .map { preferences ->
//            preferences[IS_THEME_SAME_AS_SYSTEM] ?: true
//        }

    val preferencesFlow: Flow<PreferencesUiState> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            PreferencesUiState(
                preferences[IS_THEME_SAME_AS_SYSTEM] ?: true,
                preferences[IS_MANUAL_DARK_THEME_ON] ?: false,
            )
        }

    suspend fun savePreferenceSameAsSystem(
        isThemeSameAsSystem: Boolean,
    ) {
        dataStore.edit { preferences ->
            preferences[IS_THEME_SAME_AS_SYSTEM] = isThemeSameAsSystem
        }
    }

    suspend fun savePreferenceManualTheme(
        isManualDarkThemeOn: Boolean
    ) {
        dataStore.edit { preferences ->
            preferences[IS_MANUAL_DARK_THEME_ON] = isManualDarkThemeOn
        }
    }

    private companion object {
        val IS_THEME_SAME_AS_SYSTEM = booleanPreferencesKey("is_theme_same_as_system")
        val IS_MANUAL_DARK_THEME_ON = booleanPreferencesKey("is_manual_dark_theme_on")
        const val TAG = "UserPreferencesRepo"
    }
}