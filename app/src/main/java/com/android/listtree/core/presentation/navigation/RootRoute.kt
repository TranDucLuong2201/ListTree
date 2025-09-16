package com.android.listtree.core.presentation.navigation

import kotlinx.serialization.Serializable

/*
 * Hackathon: NAVER Vietnam AI Hackathon
 * Repository: https://github.com/NAVER-Vietnam-AI-Hackathon/mobile-track-naver-vietnam-ai-hackathon-TranDucLuong2201
 * Author: DucLuong
 * Last updated: 9/16/2025
 */

sealed class RootRoute {

    @Serializable
    data object AuthNavGraph : RootRoute()

    @Serializable
    data object OnboardingNavGraph : RootRoute()

}