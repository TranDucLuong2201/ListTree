package com.android.listtree.feature.onboarding.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.android.listtree.core.presentation.utils.ThemeOption
import com.android.listtree.feature.onboarding.domain.repository.PreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/*
 * Hackathon: NAVER Vietnam AI Hackathon
 * Repository: https://github.com/NAVER-Vietnam-AI-Hackathon/mobile-track-naver-vietnam-ai-hackathon-TranDucLuong2201
 * Author: DucLuong
 * Last updated: 9/16/2025
 */

class PreferencesRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : PreferencesRepository {

    companion object {
        private const val PREFERENCES_NAME = "app_preferences"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
        private const val IS_ONBOARDED = "is_onboarded"
        private const val SELECTED_THEME = "selected_theme"

        private const val SESSION_LOGIN_TOKEN = "session_login_token"
    }

    private object PreferenceKeys {
        val IS_ONBOARDED = booleanPreferencesKey(Companion.IS_ONBOARDED)
        val SELECTED_THEME = stringPreferencesKey(Companion.SELECTED_THEME)

        val SESSION_LOGIN_TOKEN = stringPreferencesKey(Companion.SESSION_LOGIN_TOKEN)
    }


    override suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.IS_ONBOARDED] = completed
        }
    }

    override fun getOnboardingCompleted(): Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferenceKeys.IS_ONBOARDED] ?: false
        }

    override suspend fun setTheme(themeOption: ThemeOption) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.SELECTED_THEME] = themeOption.displayName
        }
    }

    override fun getTheme(): Flow<ThemeOption> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val themeName = preferences[PreferenceKeys.SELECTED_THEME]
            when (themeName) {
                ThemeOption.LIGHT.displayName -> ThemeOption.LIGHT
                ThemeOption.DARK.displayName -> ThemeOption.DARK
                else -> ThemeOption.SYSTEM
            }
        }

    override suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.SESSION_LOGIN_TOKEN] = ""
        }
    }


    override suspend fun saveSessionToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.SESSION_LOGIN_TOKEN] = token
        }
    }

    override fun getSessionToken(): Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferenceKeys.SESSION_LOGIN_TOKEN]
        }
}