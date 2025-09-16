package com.android.listtree.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.listtree.core.presentation.animation.AnimateScreen

/*
 * Hackathon: NAVER Vietnam AI Hackathon
 * Repository: https://github.com/NAVER-Vietnam-AI-Hackathon/mobile-track-naver-vietnam-ai-hackathon-TranDucLuong2201
 * Author: DucLuong
 * Last updated: 9/16/2025
 */

@Composable
fun RootNavGraph(
    rootNavController: NavHostController,
    startDestination: RootRoute,
    onBackOrFinish: () -> Unit
) {
    NavHost(
        navController = rootNavController,
        startDestination = startDestination
    ) {
        composable<RootRoute.OnboardingNavGraph>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {

        }
        composable<RootRoute.AuthNavGraph>() {  }
    }
}