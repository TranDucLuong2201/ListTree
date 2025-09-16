package com.android.listtree.core.presentation.utils

import com.android.listtree.core.presentation.navigation.RootRoute

object Utils {
    fun getStartDestination(isReady: Boolean): RootRoute {
        return if (isReady) HomeNavGraph
        else AuthNavGraph
    }
}