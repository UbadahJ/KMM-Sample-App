plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            transitiveExport = true

            linkerOpts("-lsqlite3")
            export(project(":shared:art"))
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.bundles.common)
                implementation(libs.bundles.di)

                api(project(":shared:art"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.test)
            }
        }
    }
}

android {
    namespace = "io.ubadah.art"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
