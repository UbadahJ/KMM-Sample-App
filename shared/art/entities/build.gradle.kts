import java.util.Properties
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.detekt)
    id("maven-publish")
}

val dep = internal.kmm.art.get()
group = "${dep.group}.${dep.name}"
version = dep.version ?: throw IllegalStateException("Version is not set")

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }

        publishLibraryVariants("debug", "release")
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "entities"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.bundles.common)
                implementation(libs.bundles.di)
                implementation(libs.bundles.serialization)
            }
        }
    }
}

val props = Properties().apply {
    rootProject.file("local.properties").inputStream().use {
        load(it)
    }
}

configure<PublishingExtension> {
    publishing {
        repositories {
            maven(url = "https://repo.repsy.io/mvn/vyro/internal") {
                credentials {
                    username = props.getProperty("repsyUsername")
                    password = props.getProperty("repsyPassword")
                }
            }
        }
    }
}
android {
    namespace = "io.ubadah.art.entities"
    compileSdk = libs.versions.sdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
}

detekt {
    buildUponDefaultConfig = true
    parallel = true
    autoCorrect = true
    reportsDir = file("$buildDir/reports/detekt")

    dependencies {
        detektPlugins(libs.detekt.formatting)
    }
}