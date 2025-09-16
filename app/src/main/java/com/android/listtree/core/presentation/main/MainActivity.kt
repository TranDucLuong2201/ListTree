package com.android.listtree.core.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.listtree.core.presentation.navigation.RootNavGraph
import com.android.listtree.core.presentation.theme.ListTreeTheme
import com.android.listtree.core.presentation.utils.ThemeOption
import com.android.listtree.core.presentation.utils.Utils.getStartDestination
import com.android.listtree.feature.onboarding.domain.repository.PreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	@Inject
	lateinit var preferences: PreferencesRepository

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val isReady = MutableStateFlow<Boolean?>(null)
		val themeState = MutableStateFlow(ThemeOption.SYSTEM)


		lifecycleScope.launch {
            coroutineScope {
                launch(Dispatchers.Default) {
                    isReady.value = preferences.getOnboardingCompleted().first()
                }
                launch(Dispatchers.IO) {
                    preferences.getTheme().collect {
                        themeState.value = it
                    }
                }
            }
		}
		val splashScreen = installSplashScreen()

		splashScreen.setKeepOnScreenCondition { isReady.value == null }
		setContent {
			val readyState by isReady.collectAsStateWithLifecycle()
			val theme by themeState.collectAsStateWithLifecycle()

			readyState?.let { onboarded ->
				val rootNavController = rememberNavController()
                ListTreeTheme(themeOption = theme) {
                    RootNavGraph(
                        rootNavController = rootNavController,
                        startDestination = getStartDestination(onboarded),
                        onBackOrFinish = { handleBackClick(rootNavController) }
                    )
                }
			}
		}
	}

	private fun handleBackClick(rootNavController: NavHostController) {
		if (rootNavController.previousBackStackEntry == null) finish()
		else rootNavController.navigateUp()
	}
}