plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt)
	kotlin("plugin.serialization") version "1.9.10"
}

android {
	namespace = "com.android.listtree"
	compileSdk = libs.versions.compileSdk.get().toInt()

	defaultConfig {
		applicationId = "com.android.listtree"
		minSdk = libs.versions.minSdk.get().toInt()
		targetSdk = libs.versions.targetSdk.get().toInt()
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro",
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlin {
		compilerOptions {
			optIn.add("kotlin.RequiresOptIn")
		}
	}
	buildFeatures {
		compose = true
		buildConfig = true
	}
}

dependencies {

	// App dependencies
	implementation(libs.androidx.annotation)
	implementation(libs.kotlinx.coroutines.android)
	implementation(libs.timber)
	implementation(libs.androidx.test.espresso.idling.resources)

	// Architecture Components
	implementation(libs.androidx.lifecycle.runtimeCompose)
	implementation(libs.androidx.lifecycle.viewModelCompose)

// Or the latest stable version
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.compose.ui)
	implementation(libs.androidx.compose.ui.graphics)
	implementation(libs.androidx.compose.ui.tooling.preview)
	implementation(libs.androidx.compose.material3)
	implementation(libs.androidx.material3)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.compose.ui.test.junit4)
	debugImplementation(libs.androidx.compose.ui.tooling)
	debugImplementation(libs.androidx.compose.ui.test.manifest)
	testImplementation(kotlin("test"))
	implementation(libs.bundles.dagger.hilt)
	ksp(libs.dagger.hilt.compiler)

	// fonts and extended icon
	implementation(libs.google.font)
	implementation(libs.androidx.material)
	implementation(libs.material)
	implementation(libs.androidx.dataStore.core)
	implementation(libs.androidx.dataStore.preferences)
	implementation(libs.androidx.core.splashscreen)
	implementation(libs.androidx.compose.material.icons.extended)
	implementation(libs.coil.compose) // Cho AsyncImage
	implementation(libs.androidx.compose.material3.adaptive.navigation)
	implementation(libs.androidx.navigation.compose)

	implementation(platform("io.github.jan-tennert.supabase:bom:3.2.3"))
	implementation("io.github.jan-tennert.supabase:postgrest-kt")
	implementation("io.github.jan-tennert.supabase:auth-kt")
	implementation("io.github.jan-tennert.supabase:realtime-kt")
	implementation("io.github.jan-tennert.supabase:storage-kt")
	implementation("androidx.compose.animation:animation-graphics:1.9.1")

	implementation(libs.androidx.credentials)
	implementation(libs.androidx.credentials.play.services.auth)
	implementation(libs.googleid)

	implementation(libs.ktor.client.android)
}
