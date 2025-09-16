package com.android.listtree.di

import com.android.listtree.feature.onboarding.data.repository.PreferencesRepositoryImpl
import com.android.listtree.feature.onboarding.domain.repository.PreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * Hackathon: NAVER Vietnam AI Hackathon
 * Repository: https://github.com/NAVER-Vietnam-AI-Hackathon/mobile-track-naver-vietnam-ai-hackathon-TranDucLuong2201
 * Author: DucLuong
 * Last updated: 9/16/2025
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPreferenceRepository(
        impl: PreferencesRepositoryImpl
    ): PreferencesRepository
}