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
            baseName = "remote"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared:art:data"))
                implementation(project(":shared:art:entities"))

                implementation(libs.bundles.common)
                implementation(libs.bundles.serialization)
                implementation(libs.bundles.network)
                implementation(libs.bundles.di)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.bundles.network.android)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.bundles.network.ios)
            }
        }
    }
}

android {
    namespace = "io.ubadah.art.remote"
    compileSdk = libs.versions.sdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
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

detekt {
    buildUponDefaultConfig = true
    parallel = true
    autoCorrect = true
    reportsDir = file("$buildDir/reports/detekt")

    dependencies {
        detektPlugins(libs.detekt.formatting)
    }
}