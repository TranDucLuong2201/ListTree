package com.android.listtree.feature.onboarding.domain.repository

import com.android.listtree.core.presentation.utils.ThemeOption
import kotlinx.coroutines.flow.Flow

/*
 * Hackathon: NAVER Vietnam AI Hackathon
 * Repository: https://github.com/NAVER-Vietnam-AI-Hackathon/mobile-track-naver-vietnam-ai-hackathon-TranDucLuong2201
 * Author: DucLuong
 * Last updated: 9/16/2025
 */

interface PreferencesRepository {
    suspend fun setOnboardingCompleted(completed: Boolean)
    fun getOnboardingCompleted(): Flow<Boolean>

    suspend fun setTheme(themeOption: ThemeOption)
    fun getTheme(): Flow<ThemeOption>

    suspend fun clearSession()

    suspend fun saveSessionToken(token: String)
    fun getSessionToken(): Flow<String?>
}