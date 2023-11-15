@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.app)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.detekt)
}


android {
    namespace = "io.ubadah.art.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "io.ubadah.art.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4";
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/*.kotlin_module"
            excludes += "/META-INF/versions/**"
        }
    }
}

dependencies {
    implementation(project(":shared"))

    implementation(libs.bundles.common)
    implementation(libs.bundles.core.android)

    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))

    implementation(libs.bundles.ui.android)
    implementation(libs.bundles.ui.navigation.android)
    ksp(libs.bundles.ui.navigation.android.ksp)

    implementation(libs.markdown)
    implementation(libs.androidx.splashscreen)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.permissions)

    implementation(libs.bundles.serialization)
    implementation(libs.bundles.di.android)
}

detekt {
    buildUponDefaultConfig = true
    parallel = true
    autoCorrect = true
    reportsDir = file("$buildDir/reports/detekt")
    config.setFrom(files("detekt-config.yml"))

    dependencies {
        detektPlugins(libs.detekt.formatting)
        detektPlugins(libs.detekt.compose)
    }
}