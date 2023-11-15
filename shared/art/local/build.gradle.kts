import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.sqldelight)
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
            baseName = "local"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared:art:entities"))
                implementation(project(":shared:art:data"))
                implementation(libs.bundles.common)
                implementation(libs.sqldelight.extensions)
                implementation(libs.bundles.di)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.sqldelight.android)
                implementation(libs.bundles.di)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.sqldelight.native)
                implementation(libs.sqldelight.native.driver)
                implementation(libs.bundles.di)
            }
        }
    }
}

sqldelight {
    databases {
        create("LocalDatabase") {
            packageName.set("io.ubadah.art.db")
        }
    }
}

android {
    namespace = "io.ubadah.art.local"
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

tasks.withType<Detekt>().configureEach {
    exclude("**/ai/vyro/art/db/**")
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    exclude("**/ai/vyro/art/db/**")
}