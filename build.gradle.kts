import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.kotlin.dsl.withType

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.gradle.spotless) apply false
    alias(libs.plugins.ben.manes)
    alias(libs.plugins.littlerobots)
    alias(libs.plugins.detekt)
}
/**
 * Detekt config
 */
detekt {
    toolVersion = "1.23.8"
    config.setFrom(file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}

tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}
/**
 * Utility function
 */
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = Regex("^[0-9,.v-]+(-r)?$")
    return !stableKeyword && !regex.matches(version)
}
/**
 * Utility function
 */
tasks.withType<DependencyUpdatesTask>().configureEach {
    resolutionStrategy {
        componentSelection {
            all {
                rejectVersionIf {
                    val candidateIsNonStable = isNonStable(candidate.version)
                    val currentIsStable = !isNonStable(currentVersion)
                    candidateIsNonStable && currentIsStable
                }
            }
        }
    }
}
/**
 * Subprojects config
 */
subprojects {
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "com.github.ben-manes.versions")
}
/**
 * Version catalog update
 */
versionCatalogUpdate {
    sortByKey.set(true)
    keep {
        keepUnusedVersions.set(true)
    }
}
/**
 * Allprojects config
 */
allprojects {
    plugins.withId("com.diffplug.spotless") {
        configure<com.diffplug.gradle.spotless.SpotlessExtension> {
            val ktlintVersion = rootProject.libs.versions.ktlint.get()
            kotlin {
                ktlint(ktlintVersion).editorConfigOverride(
                    mapOf("ktlint_standard_function-naming" to "disabled")
                )
                target("**/*.kt")
                targetExclude("**/Res.kt", "**/build/**/*.kt")
                trimTrailingWhitespace()
                leadingSpacesToTabs()
                endWithNewline()
            }
            kotlinGradle {
                target("**/*.gradle.kts", "*.gradle.kts")
                targetExclude("**/build/**/*.kts")
                ktlint(ktlintVersion)
                trimTrailingWhitespace()
                leadingSpacesToTabs()
                endWithNewline()
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
            freeCompilerArgs.set(
                listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlin.ExperimentalStdlibApi",
                    "-opt-in=kotlin.time.ExperimentalTime",
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=kotlinx.coroutines.FlowPreview"
                )
            )
        }
    }
}
/**
 * Custom tasks
 */

// Generate Detekt config
tasks.register("generateDetektConfig") {
    group = "maintenance"
    description = "Generate default Detekt config if not exists"
    doLast {
        val configFile = file("$rootDir/config/detekt/detekt.yml")
        if (!configFile.exists()) {
            configFile.parentFile.mkdirs()
            configFile.writeText(
                """
                # Detekt default configuration
                build:
                  maxIssues: 10
                  weights:
                    complexity: 2
                    LongParameterList: 1
                style:
                  WildcardImport:
                    active: true
                # Thêm các rules khác theo ý bạn...
                """.trimIndent()
            )
            println("Detekt config generated at ${configFile.path}")
        } else {
            println("Detekt config already exists at ${configFile.path}")
        }
    }
}

// Spotless format
tasks.register("formatCode") {
    group = "maintenance"
    description = "Apply Spotless to format all Kotlin and Gradle files"
    dependsOn(":app:spotlessApply")
    doLast {
        println("Code formatted with Spotless")
    }
}

// Check dependencies
tasks.register("checkDependencies") {
    group = "maintenance"
    description = "Check for new dependency versions"
    dependsOn(":dependencyUpdates")
    doLast {
        println("Dependency update check completed")
    }
}

// Update version catalog
tasks.register("updateVersionCatalog") {
    group = "maintenance"
    description = "Update libs.versions.toml with latest versions"
    dependsOn(":versionCatalogUpdate")
    doLast {
        println("Version catalog updated at ${rootDir}/gradle/libs.versions.toml")
    }
}

// All-in-one task
tasks.register("projectMaintenance") {
    group = "maintenance"
    description = "Run code formatting, detekt config, dependency check, and catalog update together"
    dependsOn("generateDetektConfig", "formatCode", "checkDependencies", "updateVersionCatalog")
    doLast {
        println("Project maintenance completed: Detekt config, Spotless, dependency updates done")
    }
}
