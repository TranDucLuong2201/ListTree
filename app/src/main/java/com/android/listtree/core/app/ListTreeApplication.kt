package com.android.listtree.core.app

import android.app.Application
import com.android.listtree.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/*
 * Hackathon: NAVER Vietnam AI Hackathon
 * Repository: https://github.com/NAVER-Vietnam-AI-Hackathon/mobile-track-naver-vietnam-ai-hackathon-TranDucLuong2201
 * Author: DucLuong
 * Last updated: 9/16/2025
 */

@HiltAndroidApp
class ListTreeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}